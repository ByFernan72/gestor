import React, { useState, useEffect, useCallback, useMemo } from 'react';
import {
  FiMenu,
  FiChevronLeft,
  FiChevronRight,
  FiArrowRight,
  FiRepeat,
  FiClock,
  FiSend,
  FiInbox,
  FiRefreshCw,
  FiSearch,
  FiX,
} from 'react-icons/fi';
import Footer from '../components/Footer';
import ModalTransferencia from '../components/ModalTransferencia';
import CustomSelect from '../components/CustomSelect';
import api from '../api/client';
import { MovimientoResumenDto, PageResponse } from '../types/api';
import './css/Historial.css';

export interface HistorialProps {
  onMenuClick?: () => void;
}

export default function Historial({ onMenuClick }: HistorialProps): React.JSX.Element {
  const [pagina, setPagina] = useState(0);
  const [ordenHistorial, setOrdenHistorial] = useState('fechaMovimiento,desc');
  const [busqueda, setBusqueda] = useState('');
  const [transferenciasData, setTransferenciasData] = useState<PageResponse<MovimientoResumenDto>>({
    content: [],
    totalPages: 0,
    totalElements: 0,
    number: 0,
  });
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [modalTransferenciaAbierto, setModalTransferenciaAbierto] = useState(false);

  const traerHistorial = useCallback(async () => {
    try {
      setCargando(true);
      setError(null);
      const res = await api.get<PageResponse<MovimientoResumenDto>>(
        `/movimiento/historial/transferencias?page=${pagina}&size=10&sort=${ordenHistorial}`
      );
      setTransferenciasData(res.data || { content: [], totalPages: 0, totalElements: 0, number: 0 });
    } catch (err) {
      console.error('Error al traer historial de transferencias:', err);
      setError('No se pudo obtener el historial de transferencias.');
    } finally {
      setCargando(false);
    }
  }, [pagina, ordenHistorial]);

  useEffect(() => {
    traerHistorial();
  }, [traerHistorial]);

  const {
    content: transferencias = [],
    totalPages = 0,
    totalElements = 0,
  } = transferenciasData;

  const formatearFecha = (fechaStr?: string): string => {
    if (!fechaStr) return 'N/A';
    try {
      const fecha = new Date(fechaStr);
      return fecha.toLocaleString('es-ES', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
      });
    } catch {
      return fechaStr;
    }
  };

  // Filtrado reactivo en cliente por término de búsqueda (origen, destino, activo)
  const transferenciasFiltradas = useMemo(() => {
    if (!busqueda.trim()) return transferencias;
    const term = busqueda.toLowerCase();
    return transferencias.filter(
      (item) =>
        item.carteraOrigen?.toLowerCase().includes(term) ||
        item.carteraDestino?.toLowerCase().includes(term) ||
        item.activo?.toLowerCase().includes(term) ||
        item.activoRecibido?.toLowerCase().includes(term)
    );
  }, [transferencias, busqueda]);

  return (
    <main className="main-content">
      {onMenuClick && (
        <button className="hamburger-btn" onClick={onMenuClick} aria-label="Abrir menú">
          <FiMenu />
        </button>
      )}

      <div className="historial-widget">
        <div className="historial-widget-header">
          <div className="historial-header-title">
            <FiClock className="historial-main-icon" />
            <div>
              <h2>Historial de Transferencias</h2>
              <p className="historial-subtitle">Registro cronológico de movimientos entre carteras</p>
            </div>
          </div>

          <div className="historial-header-actions">
            <div className="search-box historial-header-search">
              <FiSearch className="search-icon" />
              <input
                type="text"
                placeholder="Buscar por cartera o activo..."
                value={busqueda}
                onChange={(e) => setBusqueda(e.target.value)}
                className="search-input"
              />
              {busqueda && (
                <button
                  type="button"
                  className="clear-search-btn"
                  onClick={() => setBusqueda('')}
                  title="Limpiar búsqueda"
                >
                  <FiX />
                </button>
              )}
            </div>

            <div className="filter-group historial-header-filter">
              <CustomSelect
                className="header-custom-select"
                options={[
                  { value: 'fechaMovimiento,desc', label: 'Más recientes primero' },
                  { value: 'fechaMovimiento,asc', label: 'Más antiguas primero' },
                  { value: 'enviado,desc', label: 'Mayor cantidad enviada' },
                  { value: 'enviado,asc', label: 'Menor cantidad enviada' },
                ]}
                value={ordenHistorial}
                onChange={(val) => {
                  setPagina(0);
                  setOrdenHistorial(String(val));
                }}
              />
            </div>

            <button
              type="button"
              className="btn-secondary-action"
              onClick={traerHistorial}
              title="Refrescar datos"
            >
              <FiRefreshCw className={cargando ? 'spin' : ''} />
            </button>
            <button
              type="button"
              className="btn-primary btn-new-transfer"
              onClick={() => setModalTransferenciaAbierto(true)}
            >
              <FiRepeat />
              Nueva Transferencia
            </button>
          </div>
        </div>

        <div className="historial-widget-body">
          {cargando && transferencias.length === 0 ? (
            <div className="historial-loading">
              <FiRefreshCw className="spin" style={{ fontSize: '2rem', marginBottom: '12px' }} />
              <p>Cargando historial de transferencias...</p>
            </div>
          ) : error ? (
            <div className="historial-error">
              <p>⚠️ {error}</p>
              <button type="button" className="btn-retry" onClick={traerHistorial}>
                Reintentar
              </button>
            </div>
          ) : transferenciasFiltradas.length === 0 ? (
            <div className="historial-empty">
              <FiRepeat className="empty-icon" />
              <h3>{busqueda ? 'Sin resultados' : 'Sin transferencias registradas'}</h3>
              <p>
                {busqueda
                  ? 'No hay transferencias que coincidan con la búsqueda.'
                  : 'Realiza tu primera transferencia entre carteras para verla reflejada aquí.'}
              </p>
              {!busqueda && (
                <button
                  type="button"
                  className="btn-primary"
                  onClick={() => setModalTransferenciaAbierto(true)}
                  style={{ marginTop: '16px' }}
                >
                  <FiRepeat />
                  Realizar Transferencia
                </button>
              )}
            </div>
          ) : (
            <div className="table-responsive historial-table-container">
              <table className="historial-table">
                <thead>
                  <tr>
                    <th>Fecha</th>
                    <th>Cartera Origen</th>
                    <th>Activo Enviado</th>
                    <th>Cantidad Enviada</th>
                    <th className="th-arrow"></th>
                    <th>Cartera Destino</th>
                    <th>Activo Recibido</th>
                    <th>Cantidad Recibida</th>
                  </tr>
                </thead>
                <tbody>
                  {transferenciasFiltradas.map((item, idx) => (
                    <tr key={item.idMovimiento || item.id || idx} className="historial-row">
                      <td className="cell-date">
                        <span className="date-badge">{formatearFecha(item.fechaMovimiento)}</span>
                      </td>

                      {/* Origen */}
                      <td className="cell-wallet-origin">
                        <div className="wallet-pill origin-pill">
                          <FiSend className="pill-icon" />
                          <span>{item.carteraOrigen}</span>
                        </div>
                      </td>
                      <td className="cell-asset">{item.activo}</td>
                      <td className="cell-amount origin-amount">
                        -{(item.enviado || 0).toLocaleString('es-ES', { minimumFractionDigits: 2 })}
                      </td>

                      {/* Flecha */}
                      <td className="cell-arrow">
                        <div className="arrow-badge">
                          <FiArrowRight />
                        </div>
                      </td>

                      {/* Destino */}
                      <td className="cell-wallet-destination">
                        <div className="wallet-pill destination-pill">
                          <FiInbox className="pill-icon" />
                          <span>{item.carteraDestino}</span>
                        </div>
                      </td>
                      <td className="cell-asset">{item.activoRecibido}</td>
                      <td className="cell-amount destination-amount">
                        +{(item.recibido || 0).toLocaleString('es-ES', { minimumFractionDigits: 2 })}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>

        {/* Footer con Paginación */}
        {totalPages > 0 && (
          <div className="historial-widget-footer">
            <span className="historial-count">
              Página {pagina + 1} de {totalPages} ({totalElements} transferencias en total)
            </span>
            <div className="historial-pagination">
              <button
                type="button"
                className="pagination-btn"
                onClick={() => setPagina((prev) => Math.max(0, prev - 1))}
                disabled={pagina === 0 || cargando}
                title="Página anterior"
              >
                <FiChevronLeft />
              </button>
              <span className="page-indicator">{pagina + 1}</span>
              <button
                type="button"
                className="pagination-btn"
                onClick={() => setPagina((prev) => Math.min(totalPages - 1, prev + 1))}
                disabled={pagina >= totalPages - 1 || cargando}
                title="Página siguiente"
              >
                <FiChevronRight />
              </button>
            </div>
          </div>
        )}
      </div>

      {/* Modal de Transferencia */}
      <ModalTransferencia
        isOpen={modalTransferenciaAbierto}
        onClose={() => setModalTransferenciaAbierto(false)}
        onSuccess={() => {
          setPagina(0);
          traerHistorial();
        }}
      />

      <Footer />
    </main>
  );
}
