import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FiMenu, FiPlus, FiTrash2, FiSave, FiInfo } from 'react-icons/fi';
import Footer from '../components/Footer';
import CustomSelect from '../components/CustomSelect';
import api from '../api/client';
import { TipoActivo, CrearCarteraPayload } from '../types/api';
import './css/NuevaCartera.css';

export interface NuevaCarteraProps {
  onMenuClick?: () => void;
}

interface DraftItem {
  id: number;
  nombre: string;
  tipo: TipoActivo;
  balance: string;
}

export default function NuevaCartera({ onMenuClick }: NuevaCarteraProps): React.JSX.Element {
  const navigate = useNavigate();

  const [nombre, setNombre] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [activos, setActivos] = useState<DraftItem[]>([]);
  const [guardando, setGuardando] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // ── Gestión de activos ──────────────────────────────────────────
  const añadirActivo = () => {
    setActivos((prev) => [
      ...prev,
      { id: Date.now(), nombre: '', tipo: 'FIAT', balance: '' },
    ]);
  };

  const actualizarActivo = (id: number, campo: keyof DraftItem, valor: any) => {
    setActivos((prev) =>
      prev.map((a) => {
        if (a.id === id) {
          let nuevoValor = valor;
          // Validación de balance no negativo
          if (campo === 'balance') {
            const numero = parseFloat(String(valor).replace(',', '.'));
            if (!isNaN(numero) && numero < 0) {
              nuevoValor = '0';
            }
          }
          return { ...a, [campo]: nuevoValor };
        }
        return a;
      })
    );
  };

  const eliminarActivo = (id: number) => {
    setActivos((prev) => prev.filter((a) => a.id !== id));
  };

  // ── Cálculo del total estimado ─────────────────────────────────
  const totalEstimado = activos.reduce((sum, a) => {
    const balance = parseFloat(String(a.balance).replace(',', '.')) || 0;
    return sum + balance;
  }, 0);

  // ── Submit ──────────────────────────────────────────────────────
  const handleGuardar = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!nombre.trim()) {
      setError('El nombre de la cartera es obligatorio.');
      return;
    }
    setGuardando(true);
    setError(null);
    try {
      const payload: CrearCarteraPayload = {
        nombreCartera: nombre.trim(),
        descripcion: descripcion.trim(),
        activos: activos.map((a) => ({
          nombreActivo: a.nombre,
          tipo: a.tipo,
          balance: parseFloat(String(a.balance).replace(',', '.')) || 0,
        })),
      };
      await api.post('/cartera/crearCartera', payload);
      navigate('/cartera');
    } catch (err) {
      console.error('Error al crear la cartera:', err);
      setError('No se pudo crear la cartera. Inténtalo de nuevo.');
    } finally {
      setGuardando(false);
    }
  };

  return (
    <main className="main-content">
      {onMenuClick && (
        <button className="hamburger-btn" onClick={onMenuClick} aria-label="Abrir menú">
          <FiMenu />
        </button>
      )}

      {/* Breadcrumb */}
      <nav className="nc-breadcrumb">
        <span className="nc-breadcrumb-link" onClick={() => navigate('/cartera')}>
          Carteras
        </span>
        <span className="nc-breadcrumb-sep">›</span>
        <span className="nc-breadcrumb-current">Nueva Cartera</span>
      </nav>

      <h1 className="nc-page-title">Crear Nueva Cartera</h1>

      {/* ── Widget principal (único contenedor) ── */}
      <form className="nc-widget" onSubmit={handleGuardar}>
        {/* ── Cuerpo: dos columnas ── */}
        <div className="nc-widget-body">
          {/* Columna izquierda – Información general */}
          <div className="nc-col-left">
            <h2 className="nc-section-title">Información General</h2>

            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <label className="nc-label">
                Nombre de la Cartera <span className="nc-required">*</span>
              </label>
              <span
                style={{
                  fontSize: '0.78rem',
                  color: nombre.length > 100 ? '#ef4444' : 'var(--text-secondary, #94a3b8)',
                  fontWeight: nombre.length > 100 ? 700 : 500,
                }}
              >
                {nombre.length}/100
              </span>
            </div>
            <input
              className="nc-input"
              type="text"
              placeholder="Ej: Ahorro Jubilación"
              value={nombre}
              onChange={(e) => setNombre(e.target.value)}
              style={nombre.length > 100 ? { borderColor: '#ef4444' } : undefined}
              required
            />
            {nombre.length > 100 && (
              <span style={{ fontSize: '0.75rem', color: '#ef4444', marginTop: '4px', display: 'block' }}>
                Has superado el máximo de 100 caracteres por {nombre.length - 100}.
              </span>
            )}

            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: '16px' }}>
              <label className="nc-label">
                Descripción
              </label>
              <span
                style={{
                  fontSize: '0.78rem',
                  color: descripcion.length > 500 ? '#ef4444' : 'var(--text-secondary, #94a3b8)',
                  fontWeight: descripcion.length > 500 ? 700 : 500,
                }}
              >
                {descripcion.length}/500
              </span>
            </div>
            <textarea
              className="nc-textarea"
              placeholder="Detalles u objetivos de inversión..."
              value={descripcion}
              onChange={(e) => setDescripcion(e.target.value)}
              rows={5}
              style={descripcion.length > 500 ? { borderColor: '#ef4444' } : undefined}
            />
            {descripcion.length > 500 && (
              <span style={{ fontSize: '0.75rem', color: '#ef4444', marginTop: '4px', display: 'block' }}>
                Has superado el máximo de 500 caracteres por {descripcion.length - 500}.
              </span>
            )}

            {/* Info Box */}
            <div className="nc-info-box">
              <FiInfo className="nc-info-icon" />
              <p>
                Las carteras nuevas se activan por defecto y comenzarán a monitorizar datos de mercado inmediatamente.
              </p>
            </div>
          </div>

          {/* Columna derecha – Tabla de activos */}
          <div className="nc-col-right">
            <div className="nc-assets-header">
              <h2 className="nc-section-title">Activos de la Cartera</h2>
              <button
                type="button"
                className="btn-primary nc-btn-add"
                onClick={añadirActivo}
              >
                <FiPlus />
                Añadir Activo
              </button>
            </div>

            <div className="nc-table-wrapper">
              <table className="nc-table">
                <thead>
                  <tr>
                    <th>Nombre del Activo</th>
                    <th>Tipo</th>
                    <th>Balance / Valor Total</th>
                    <th>Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  {activos.length === 0 ? (
                    <tr>
                      <td colSpan={4} className="nc-table-empty">
                        Sin activos. Pulsa «+ Añadir Activo» para empezar.
                      </td>
                    </tr>
                  ) : (
                    activos.map((activo) => (
                      <tr key={activo.id}>
                        <td>
                          <input
                            className="nc-cell-input"
                            type="text"
                            placeholder="S&P 500 ETF"
                            value={activo.nombre}
                            onChange={(e) =>
                              actualizarActivo(activo.id, 'nombre', e.target.value)
                            }
                          />
                        </td>
                        <td>
                          <CustomSelect
                            className="table-select"
                            options={[
                              { value: 'FIAT', label: 'Fiat' },
                              { value: 'ACCION', label: 'Acción' },
                              { value: 'ETF', label: 'ETF' },
                              { value: 'CRYPTO', label: 'Crypto' },
                              { value: 'FONDO_INDEXADO', label: 'Fondo' },
                              { value: 'BONOS_TESORO', label: 'Bonos' },
                              { value: 'OTHER', label: 'Otro' },
                            ]}
                            value={activo.tipo}
                            onChange={(val) =>
                              actualizarActivo(activo.id, 'tipo', val as TipoActivo)
                            }
                          />
                        </td>
                        <td>
                          <div className="nc-price-cell">
                            <input
                              className="nc-cell-input nc-cell-number"
                              type="number"
                              min="0"
                              step="any"
                              placeholder="0,00"
                              value={activo.balance}
                              onChange={(e) =>
                                actualizarActivo(activo.id, 'balance', e.target.value)
                              }
                            />
                            <span className="nc-currency">€</span>
                          </div>
                        </td>
                        <td>
                          <button
                            type="button"
                            className="nc-btn-delete"
                            onClick={() => eliminarActivo(activo.id)}
                            title="Eliminar activo"
                          >
                            <FiTrash2 />
                          </button>
                        </td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>

            {/* Total estimado */}
            <div className="nc-total-row">
              <span className="nc-total-label">Total Estimado</span>
              <span className="nc-total-value">
                {totalEstimado.toLocaleString('es-ES', {
                  minimumFractionDigits: 2,
                  maximumFractionDigits: 2,
                })}{' '}
                €
              </span>
            </div>
          </div>
        </div>

        {/* ── Footer del widget ── */}
        <div className="nc-widget-footer">
          <div className="nc-footer-actions">
            {error && <p className="nc-error">{error}</p>}
            <button
              type="button"
              className="nc-btn-cancel"
              onClick={() => navigate('/cartera')}
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="btn-primary"
              disabled={guardando}
            >
              <FiSave />
              {guardando ? 'Guardando...' : 'Guardar Cartera'}
            </button>
          </div>
        </div>
      </form>

      <Footer />
    </main>
  );
}
