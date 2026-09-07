import React, { useState, useEffect } from 'react';
import { FiX, FiArrowRight, FiCheckCircle, FiAlertCircle } from 'react-icons/fi';
import CustomSelect from './CustomSelect';
import api from '../api/client';
import { CarteraItemDto, ActivoDetalleDto, TipoActivo } from '../types/api';
import './ModalTransferencia.css';

export interface ModalTransferenciaProps {
  isOpen: boolean;
  onClose: () => void;
  onSuccess?: () => void;
}

export default function ModalTransferencia({
  isOpen,
  onClose,
  onSuccess,
}: ModalTransferenciaProps): React.JSX.Element | null {
  const [carteras, setCarteras] = useState<CarteraItemDto[]>([]);
  const [cargandoCarteras, setCargandoCarteras] = useState(false);

  // Origen
  const [idCarteraOrigen, setIdCarteraOrigen] = useState<string | number>('');
  const [activosOrigen, setActivosOrigen] = useState<ActivoDetalleDto[]>([]);
  const [idActivoOrigen, setIdActivoOrigen] = useState<string | number>('');
  const [cantidadOrigen, setCantidadOrigen] = useState<string | number>('');

  // Destino
  const [idCarteraDestino, setIdCarteraDestino] = useState<string | number>('');
  const [activosDestino, setActivosDestino] = useState<ActivoDetalleDto[]>([]);
  const [modoActivoDestino, setModoActivoDestino] = useState<'existente' | 'nuevo'>('existente');
  const [idActivoDestino, setIdActivoDestino] = useState<string | number>('');
  const [nombreNuevoActivo, setNombreNuevoActivo] = useState('');
  const [tipoNuevoActivo, setTipoNuevoActivo] = useState<TipoActivo>('FIAT');
  const [cantidadDestino, setCantidadDestino] = useState<string | number>('');

  // Estado del submit
  const [enviando, setEnviando] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [exito, setExito] = useState(false);

  const resetForm = () => {
    setIdCarteraOrigen('');
    setActivosOrigen([]);
    setIdActivoOrigen('');
    setCantidadOrigen('');

    setIdCarteraDestino('');
    setActivosDestino([]);
    setModoActivoDestino('existente');
    setIdActivoDestino('');
    setNombreNuevoActivo('');
    setTipoNuevoActivo('FIAT');
    setCantidadDestino('');
  };

  const cargarCarteras = async () => {
    try {
      setCargandoCarteras(true);
      const res = await api.get<CarteraItemDto[]>('/cartera');
      setCarteras(res.data || []);
    } catch (err) {
      console.error('Error al cargar carteras:', err);
      setError('No se pudieron cargar las carteras disponibles.');
    } finally {
      setCargandoCarteras(false);
    }
  };

  // Cargar carteras al abrir el modal
  useEffect(() => {
    if (isOpen) {
      setError(null);
      setExito(false);
      resetForm();
      cargarCarteras();
    }
  }, [isOpen]);

  // Cargar activos de la cartera origen seleccionada
  const handleCarteraOrigenChange = async (id: string | number) => {
    setIdCarteraOrigen(id);
    setIdActivoOrigen('');
    setActivosOrigen([]);

    if (id) {
      try {
        const res = await api.get<{ activos: ActivoDetalleDto[] }>(`/cartera/${id}`);
        setActivosOrigen(res.data?.activos || []);
      } catch (err) {
        console.error('Error al cargar activos de origen:', err);
      }
    }
  };

  // Cargar activos de la cartera destino seleccionada
  const handleCarteraDestinoChange = async (id: string | number) => {
    setIdCarteraDestino(id);
    setIdActivoDestino('');
    setActivosDestino([]);

    if (id) {
      try {
        const res = await api.get<{ activos: ActivoDetalleDto[] }>(`/cartera/${id}`);
        const list = res.data?.activos || [];
        setActivosDestino(list);
        if (list.length === 0) {
          setModoActivoDestino('nuevo');
        } else {
          setModoActivoDestino('existente');
        }
      } catch (err) {
        console.error('Error al cargar activos de destino:', err);
      }
    }
  };

  // Activo seleccionado de origen para ver su saldo maximo
  const activoOrigenSeleccionado = activosOrigen.find(
    (a) => String(a.idActivo) === String(idActivoOrigen)
  );

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    if (!idCarteraOrigen || !idCarteraDestino || !idActivoOrigen) {
      setError('Por favor completa los campos obligatorios de origen y destino.');
      return;
    }

    const cOrigenNum = parseFloat(String(cantidadOrigen).replace(',', '.'));
    const cDestinoNum = parseFloat(String(cantidadDestino).replace(',', '.'));

    if (isNaN(cOrigenNum) || cOrigenNum <= 0) {
      setError('La cantidad enviada debe ser mayor a 0.');
      return;
    }

    if (isNaN(cDestinoNum) || cDestinoNum <= 0) {
      setError('La cantidad recibida debe ser mayor a 0.');
      return;
    }

    if (activoOrigenSeleccionado && cOrigenNum > activoOrigenSeleccionado.balance) {
      setError(`Saldo insuficiente en el activo de origen (${activoOrigenSeleccionado.balance} € disponible).`);
      return;
    }

    if (modoActivoDestino === 'existente' && !idActivoDestino) {
      setError('Selecciona el activo destino o elige la opción de crear uno nuevo.');
      return;
    }

    if (
      String(idCarteraOrigen) === String(idCarteraDestino) &&
      modoActivoDestino === 'existente' &&
      String(idActivoOrigen) === String(idActivoDestino)
    ) {
      setError('No se puede transferir un activo hacia sí mismo en la misma cartera.');
      return;
    }

    if (modoActivoDestino === 'nuevo' && !nombreNuevoActivo.trim()) {
      setError('Indica el nombre del nuevo activo destino.');
      return;
    }

    try {
      setEnviando(true);

      const payload = {
        idCarteraOrigen: Number(idCarteraOrigen),
        idCarteraDestino: Number(idCarteraDestino),
        idActivoOrigen: Number(idActivoOrigen),
        idActivoDestino: modoActivoDestino === 'existente' ? Number(idActivoDestino) : null,
        cantidadOrigen: cOrigenNum,
        cantidadDestino: cDestinoNum,
        nombreNuevoActivo: modoActivoDestino === 'nuevo' ? nombreNuevoActivo.trim() : null,
        tipoNuevoActivo: modoActivoDestino === 'nuevo' ? tipoNuevoActivo : null,
      };

      await api.post('/movimiento/transferencia', payload);

      setExito(true);
      if (onSuccess) onSuccess();

      setTimeout(() => {
        onClose();
      }, 1200);
    } catch (err: any) {
      console.error('Error al transferir:', err);
      const msg = err.response?.data?.message || err.response?.data || 'Error al realizar la transferencia.';
      setError(typeof msg === 'string' ? msg : 'Error al procesar la transferencia.');
    } finally {
      setEnviando(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-container modal-transfer-container">
        <div className="modal-header">
          <div className="modal-header-title">
            <FiArrowRight className="header-icon" />
            <h3>Transferir entre Carteras</h3>
          </div>
          <button type="button" className="close-btn" onClick={onClose} aria-label="Cerrar modal">
            <FiX />
          </button>
        </div>

        {exito ? (
          <div className="transfer-success">
            <FiCheckCircle className="success-icon" />
            <h4>¡Transferencia realizada con éxito!</h4>
            <p>Se han actualizado los saldos correspondientes.</p>
          </div>
        ) : (
          <form className="transfer-form" onSubmit={handleSubmit}>
            {error && (
              <div className="transfer-error">
                <FiAlertCircle />
                <span>{error}</span>
              </div>
            )}

            <div className="transfer-grid">
              {/* Bloque Origen */}
              <div className="transfer-card origin-card">
                <div className="transfer-card-badge origin">ORIGEN (SALIDA)</div>

                <div className="form-group">
                  <label>Cartera Origen</label>
                  <CustomSelect
                    options={carteras.map((c) => ({
                      value: c.idCartera,
                      label: c.nombreCartera,
                    }))}
                    value={idCarteraOrigen}
                    onChange={handleCarteraOrigenChange}
                    placeholder="-- Seleccionar cartera --"
                    disabled={cargandoCarteras || enviando}
                  />
                </div>

                <div className="form-group">
                  <label>Activo Origen</label>
                  <CustomSelect
                    options={activosOrigen.map((a) => ({
                      value: a.idActivo ?? 0,
                      label: `${a.nombreActivo} (Saldo: ${a.balance} €)`,
                    }))}
                    value={idActivoOrigen}
                    onChange={(val) => setIdActivoOrigen(val)}
                    placeholder="-- Seleccionar activo --"
                    disabled={!idCarteraOrigen || enviando}
                  />
                </div>

                <div className="form-group">
                  <label>Cantidad a Enviar</label>
                  <div className="input-with-action">
                    <input
                      type="number"
                      step="any"
                      min="0.0001"
                      placeholder="0.00"
                      className="modal-input"
                      value={cantidadOrigen}
                      onChange={(e) => {
                        setCantidadOrigen(e.target.value);
                        if (!cantidadDestino) setCantidadDestino(e.target.value);
                      }}
                      required
                      disabled={enviando}
                    />
                    {activoOrigenSeleccionado && (
                      <button
                        type="button"
                        className="btn-max"
                        onClick={() => {
                          setCantidadOrigen(activoOrigenSeleccionado.balance);
                          if (!cantidadDestino) setCantidadDestino(activoOrigenSeleccionado.balance);
                        }}
                      >
                        MAX
                      </button>
                    )}
                  </div>
                </div>
              </div>

              {/* Bloque Destino */}
              <div className="transfer-card destination-card">
                <div className="transfer-card-badge destination">DESTINO (ENTRADA)</div>

                <div className="form-group">
                  <label>Cartera Destino</label>
                  <CustomSelect
                    options={carteras.map((c) => ({
                      value: c.idCartera,
                      label: c.nombreCartera,
                    }))}
                    value={idCarteraDestino}
                    onChange={handleCarteraDestinoChange}
                    placeholder="-- Seleccionar cartera --"
                    disabled={cargandoCarteras || enviando}
                  />
                </div>

                {idCarteraDestino && (
                  <div className="destination-mode-toggle">
                    <button
                      type="button"
                      className={`mode-btn ${modoActivoDestino === 'existente' ? 'active' : ''}`}
                      onClick={() => setModoActivoDestino('existente')}
                      disabled={activosDestino.length === 0}
                    >
                      Activo Existente
                    </button>
                    <button
                      type="button"
                      className={`mode-btn ${modoActivoDestino === 'nuevo' ? 'active' : ''}`}
                      onClick={() => setModoActivoDestino('nuevo')}
                    >
                      + Nuevo Activo
                    </button>
                  </div>
                )}

                {modoActivoDestino === 'existente' ? (
                  <div className="form-group">
                    <label>Activo Destino</label>
                    <CustomSelect
                      options={activosDestino
                        .filter(
                          (a) =>
                            String(idCarteraOrigen) !== String(idCarteraDestino) ||
                            String(a.idActivo) !== String(idActivoOrigen)
                        )
                        .map((a) => ({
                          value: a.idActivo ?? 0,
                          label: `${a.nombreActivo} (Saldo: ${a.balance} €)`,
                        }))}
                      value={idActivoDestino}
                      onChange={(val) => setIdActivoDestino(val)}
                      placeholder="-- Seleccionar activo receptor --"
                      disabled={!idCarteraDestino || enviando}
                    />
                  </div>
                ) : (
                  <div className="new-asset-fields">
                    <div className="form-group">
                      <label>Nombre del Nuevo Activo</label>
                      <input
                        type="text"
                        placeholder="Ej: EUR, Bitcoin, S&P 500..."
                        className="modal-input"
                        value={nombreNuevoActivo}
                        onChange={(e) => setNombreNuevoActivo(e.target.value)}
                        required={modoActivoDestino === 'nuevo'}
                        disabled={enviando}
                      />
                    </div>
                    <div className="form-group">
                      <label>Tipo de Activo</label>
                      <CustomSelect
                        options={[
                          { value: 'FIAT', label: 'Fiat' },
                          { value: 'ACCION', label: 'Acción' },
                          { value: 'ETF', label: 'ETF' },
                          { value: 'CRYPTO', label: 'Crypto' },
                          { value: 'FONDO_INDEXADO', label: 'Fondo Indexado' },
                          { value: 'BONOS_TESORO', label: 'Bonos' },
                          { value: 'OTHER', label: 'Otro' },
                        ]}
                        value={tipoNuevoActivo}
                        onChange={(val) => setTipoNuevoActivo(val as TipoActivo)}
                        disabled={enviando}
                      />
                    </div>
                  </div>
                )}

                <div className="form-group">
                  <label>Cantidad Recibida</label>
                  <input
                    type="number"
                    step="any"
                    min="0.0001"
                    placeholder="0.00"
                    className="modal-input"
                    value={cantidadDestino}
                    onChange={(e) => setCantidadDestino(e.target.value)}
                    required
                    disabled={enviando}
                  />
                </div>
              </div>
            </div>

            <div className="modal-footer-actions">
              <button
                type="button"
                className="btn-modal-cancel"
                onClick={onClose}
                disabled={enviando}
              >
                Cancelar
              </button>
              <button
                type="submit"
                className="btn-modal-confirm"
                disabled={enviando}
              >
                {enviando ? 'Procesando...' : 'Confirmar Transferencia'}
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
}
