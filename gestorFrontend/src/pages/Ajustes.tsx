import React, { useState, useEffect } from 'react';
import {
  FiMenu,
  FiSettings,
  FiUser,
  FiLink,
  FiPlus,
  FiTrash2,
  FiExternalLink,
  FiSave,
  FiAlertTriangle,
  FiCheckCircle,
  FiAlertCircle,
  FiX,
} from 'react-icons/fi';
import Footer from '../components/Footer';
import ConfirmModal from '../components/ConfirmModal';
import api from '../api/client';
import { PerfilResponseDto } from '../types/api';
import './css/Ajustes.css';

export interface AjustesProps {
  onMenuClick?: () => void;
}

interface MensajeFeedback {
  tipo: 'success' | 'error';
  texto: string;
}

export default function Ajustes({ onMenuClick }: AjustesProps): React.JSX.Element {
  // Estados de Perfil
  const [nombrePerfil, setNombrePerfil] = useState('');
  const [links, setLinks] = useState<string[]>([]);
  const [nuevoLink, setNuevoLink] = useState('');
  const [cargandoPerfil, setCargandoPerfil] = useState(true);
  const [guardandoPerfil, setGuardandoPerfil] = useState(false);
  const [mensajePerfil, setMensajePerfil] = useState<MensajeFeedback | null>(null);

  // Modal para confirmación de eliminar link
  const [linkIndexToDelete, setLinkIndexToDelete] = useState<number | null>(null);

  // Estados de Zona de Peligro / Reset
  const [modalResetAbierto, setModalResetAbierto] = useState(false);
  const [confirmacionTexto, setConfirmacionTexto] = useState('');
  const [reseteando, setReseteando] = useState(false);
  const [mensajeReset, setMensajeReset] = useState<MensajeFeedback | null>(null);

  // Cargar datos del perfil
  useEffect(() => {
    cargarPerfil();
  }, []);

  // Cerrar modal de reset con Escape
  useEffect(() => {
    if (!modalResetAbierto) return;
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === 'Escape' && !reseteando) {
        setModalResetAbierto(false);
      }
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, [modalResetAbierto, reseteando]);

  const cargarPerfil = async () => {
    try {
      setCargandoPerfil(true);
      const res = await api.get<PerfilResponseDto>('/perfil/1');
      if (res.data) {
        setNombrePerfil(res.data.nombrePerfil || '');
        setLinks(res.data.links || []);
      }
    } catch (err) {
      console.error('Error al cargar perfil:', err);
      // Fallback inicial si es primer uso
      setNombrePerfil('');
      setLinks([]);
    } finally {
      setCargandoPerfil(false);
    }
  };

  const handleAgregarLink = (e: React.FormEvent) => {
    e.preventDefault();
    if (!nuevoLink.trim()) return;

    let url = nuevoLink.trim();
    if (!/^https?:\/\//i.test(url)) {
      url = 'https://' + url;
    }

    if (!links.includes(url)) {
      setLinks([...links, url]);
      setNuevoLink('');
    }
  };

  const confirmarEliminarLink = () => {
    if (linkIndexToDelete !== null) {
      setLinks(links.filter((_, i) => i !== linkIndexToDelete));
      setLinkIndexToDelete(null);
    }
  };

  const handleGuardarPerfil = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!nombrePerfil.trim()) {
      setMensajePerfil({ tipo: 'error', texto: 'El nombre de perfil no puede estar vacío.' });
      return;
    }

    try {
      setGuardandoPerfil(true);
      setMensajePerfil(null);

      const payload = {
        nombrePerfil: nombrePerfil.trim(),
        links: links,
      };

      await api.put('/perfil/1', payload);
      setMensajePerfil({ tipo: 'success', texto: '¡Perfil actualizado con éxito!' });

      setTimeout(() => {
        setMensajePerfil(null);
      }, 4000);
    } catch (err) {
      console.error('Error al guardar perfil:', err);
      setMensajePerfil({ tipo: 'error', texto: 'No se pudieron guardar los cambios del perfil.' });
    } finally {
      setGuardandoPerfil(false);
    }
  };

  const handleResetearDatos = async () => {
    if (confirmacionTexto.toUpperCase() !== 'ELIMINAR') {
      setMensajeReset({ tipo: 'error', texto: "Debes escribir 'ELIMINAR' para confirmar." });
      return;
    }

    try {
      setReseteando(true);
      setMensajeReset(null);

      await api.post('/ajustes/reset');
      setMensajeReset({ tipo: 'success', texto: 'Datos reseteados correctamente.' });

      setTimeout(() => {
        setModalResetAbierto(false);
        setConfirmacionTexto('');
        window.location.href = '/';
      }, 1500);
    } catch (err) {
      console.error('Error al resetear datos:', err);
      setMensajeReset({ tipo: 'error', texto: 'No se pudieron resetear los datos.' });
    } finally {
      setReseteando(false);
    }
  };

  return (
    <main className="main-content">
      {onMenuClick && (
        <button className="hamburger-btn" onClick={onMenuClick} aria-label="Abrir menú">
          <FiMenu />
        </button>
      )}

      <div className="ajustes-container">
        <div className="ajustes-header">
          <div className="ajustes-title-group">
            <FiSettings className="ajustes-main-icon" />
            <div>
              <h2 className="ajustes-title">Ajustes Generales</h2>
              <p className="ajustes-subtitle">Gestiona tu perfil, accesos directos y configuración de la app</p>
            </div>
          </div>
        </div>

        <div className="ajustes-content-grid">
          {/* Tarjeta de Perfil */}
          <div className="ajustes-card">
            <div className="ajustes-card-header">
              <div className="card-header-icon-badge">
                <FiUser />
              </div>
              <div>
                <h3>Perfil de Usuario</h3>
                <p>Personaliza tu información y enlaces importantes</p>
              </div>
            </div>

            {mensajePerfil && (
              <div className={`ajustes-alert ${mensajePerfil.tipo}`}>
                {mensajePerfil.tipo === 'success' ? <FiCheckCircle /> : <FiAlertCircle />}
                <span>{mensajePerfil.texto}</span>
              </div>
            )}

            {cargandoPerfil ? (
              <div className="ajustes-loading">Cargando información del perfil...</div>
            ) : (
              <form className="perfil-form" onSubmit={handleGuardarPerfil}>
                <div className="form-group">
                  <label>Nombre de Perfil</label>
                  <input
                    type="text"
                    className="modal-input"
                    value={nombrePerfil}
                    onChange={(e) => setNombrePerfil(e.target.value)}
                    placeholder="Tu nombre o apodo..."
                    required
                  />
                </div>

                <div className="form-group">
                  <label>Enlaces de Interés / Cuentas Relevantes</label>
                  <div className="add-link-box">
                    <input
                      type="text"
                      className="modal-input"
                      placeholder="Ej: binance.com, interactivebrokers.com..."
                      value={nuevoLink}
                      onChange={(e) => setNuevoLink(e.target.value)}
                    />
                    <button
                      type="button"
                      className="btn-add-link"
                      onClick={handleAgregarLink}
                    >
                      <FiPlus /> Añadir
                    </button>
                  </div>

                  {/* Lista de Enlaces */}
                  <div className="links-list">
                    {links.length === 0 ? (
                      <p className="links-empty">No has agregado enlaces todavía.</p>
                    ) : (
                      links.map((link, idx) => (
                        <div key={idx} className="link-item">
                          <FiLink className="link-icon" />
                          <a
                            href={link}
                            target="_blank"
                            rel="noopener noreferrer"
                            className="link-url"
                          >
                            {link}
                          </a>
                          <div className="link-actions">
                            <a
                              href={link}
                              target="_blank"
                              rel="noopener noreferrer"
                              className="btn-link-action"
                              title="Abrir enlace"
                            >
                              <FiExternalLink />
                            </a>
                            <button
                              type="button"
                              className="btn-link-action delete"
                              onClick={() => setLinkIndexToDelete(idx)}
                              title="Eliminar"
                            >
                              <FiTrash2 />
                            </button>
                          </div>
                        </div>
                      ))
                    )}
                  </div>
                </div>

                <div className="perfil-form-footer">
                  <button
                    type="submit"
                    className="btn-primary btn-save-perfil"
                    disabled={guardandoPerfil}
                  >
                    <FiSave />
                    {guardandoPerfil ? 'Guardando...' : 'Guardar Perfil'}
                  </button>
                </div>
              </form>
            )}
          </div>

          {/* Tarjeta Zona de Peligro */}
          <div className="ajustes-card danger-zone-card">
            <div className="ajustes-card-header">
              <div className="card-header-icon-badge danger">
                <FiAlertTriangle />
              </div>
              <div>
                <h3 className="danger-title">Zona de Peligro</h3>
                <p>Acciones destructivas e irreversibles en el almacenamiento local</p>
              </div>
            </div>

            <div className="danger-zone-body">
              <div className="danger-info-block">
                <h4>Resetear Base de Datos</h4>
                <p>
                  Esta opción eliminará permanentemente todas las carteras, activos, transacciones, historial y la información de tu perfil (nombre y enlaces guardados).
                </p>
              </div>
              <button
                type="button"
                className="btn-danger-reset"
                onClick={() => {
                  setConfirmacionTexto('');
                  setMensajeReset(null);
                  setModalResetAbierto(true);
                }}
              >
                <FiTrash2 /> Resetear Datos
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Modal de Confirmación de Eliminación de Enlace */}
      <ConfirmModal
        isOpen={linkIndexToDelete !== null}
        title="¿Eliminar enlace?"
        message={`¿Estás seguro de que deseas eliminar "${linkIndexToDelete !== null ? links[linkIndexToDelete] : ''}"?`}
        confirmText="Eliminar"
        cancelText="Cancelar"
        variant="danger"
        onConfirm={confirmarEliminarLink}
        onCancel={() => setLinkIndexToDelete(null)}
      />

      {/* Modal de Confirmación de Reset */}
      {modalResetAbierto && (
        <div
          className="modal-overlay"
          onClick={(e) => {
            if (e.target === e.currentTarget && !reseteando) {
              setModalResetAbierto(false);
            }
          }}
          role="dialog"
          aria-modal="true"
          aria-labelledby="reset-modal-title"
        >
          <div className="modal-container modal-danger-container">
            <div className="modal-header">
              <div className="modal-header-danger">
                <FiAlertTriangle className="danger-icon-large" />
                <h3 id="reset-modal-title">¿Confirmar Reset de Datos?</h3>
              </div>
              <button
                type="button"
                className="close-btn"
                onClick={() => setModalResetAbierto(false)}
                aria-label="Cerrar modal"
              >
                <FiX />
              </button>
            </div>

            <div className="modal-body">
              <p className="danger-warning-text">
                Esta acción <strong>borrará de forma irrecuperable</strong> todas tus carteras, activos, movimientos y los datos de tu perfil (nombre y enlaces).
              </p>
              <p className="danger-instruction">
                Para confirmar, escribe <strong>ELIMINAR</strong> a continuación:
              </p>

              <input
                type="text"
                className="modal-input danger-confirm-input"
                placeholder="Escribe ELIMINAR"
                value={confirmacionTexto}
                onChange={(e) => setConfirmacionTexto(e.target.value)}
                onKeyDown={(e) => {
                  if (
                    e.key === 'Enter' &&
                    confirmacionTexto.toUpperCase() === 'ELIMINAR' &&
                    !reseteando
                  ) {
                    handleResetearDatos();
                  }
                }}
                autoFocus
              />

              {mensajeReset && (
                <div className={`ajustes-alert ${mensajeReset.tipo}`} style={{ marginTop: '12px' }}>
                  {mensajeReset.tipo === 'success' ? <FiCheckCircle /> : <FiAlertCircle />}
                  <span>{mensajeReset.texto}</span>
                </div>
              )}
            </div>

            <div className="modal-footer-actions">
              <button
                type="button"
                className="btn-modal-cancel"
                onClick={() => setModalResetAbierto(false)}
                disabled={reseteando}
              >
                Cancelar
              </button>
              <button
                type="button"
                className="btn-danger-confirm"
                onClick={handleResetearDatos}
                disabled={confirmacionTexto.toUpperCase() !== 'ELIMINAR' || reseteando}
              >
                {reseteando ? 'Reseteando...' : 'Confirmar y Resetear'}
              </button>
            </div>
          </div>
        </div>
      )}

      <Footer />
    </main>
  );
}
