import { useState } from 'react';
import { FiMenu, FiRepeat, FiAlertTriangle } from 'react-icons/fi';
import Footer from '../components/Footer';
import ModalTransferencia from '../components/ModalTransferencia';
import { useFetch } from '../hooks/useFetch';
import { DashboardResumenResponseDto, PerfilResponseDto } from '../types/api';
import { DashboardSkeleton } from '../components/ui/Skeleton';
import './css/Home.css';

interface HomeProps {
  onMenuClick?: () => void;
}

export default function Home({ onMenuClick }: HomeProps) {
  const [modalTransferenciaAbierto, setModalTransferenciaAbierto] = useState(false);

  const {
    data: dashboardData,
    loading: cargando,
    error,
    refetch: traerDatos,
  } = useFetch<DashboardResumenResponseDto>('/dashboard/resumen');

  const { data: perfilData } = useFetch<PerfilResponseDto>('/perfil/1');

  const totalBalance = dashboardData?.balanceTotal ?? 0;
  const topCarteras = dashboardData?.carteras ?? [];
  const ultimosMovimientos = dashboardData?.movimientos ?? [];
  const nombreUsuario = perfilData?.nombrePerfil?.trim();

  return (
    <main className="main-content" data-testid="home-main-content">
      {onMenuClick && (
        <button className="hamburger-btn" onClick={onMenuClick} aria-label="Abrir menú">
          <FiMenu />
        </button>
      )}

      <div className="home-header-row">
        <h1 className="home-title">
          {nombreUsuario ? `Bienvenido/a, ${nombreUsuario} !` : 'Bienvenido/a !'}
        </h1>
        <button
          className="btn-primary btn-quick-transfer"
          onClick={() => setModalTransferenciaAbierto(true)}
          data-testid="btn-quick-transfer"
        >
          <FiRepeat />
          Transferir Fondos
        </button>
      </div>

      {cargando && !dashboardData ? (
        <DashboardSkeleton />
      ) : error ? (
        <div
          className="historial-error"
          style={{
            margin: '40px auto',
            maxWidth: '600px',
            padding: '32px',
            background: 'var(--bg-glass-card, rgba(255,255,255,0.05))',
            borderRadius: '20px',
            border: '1px solid var(--border-glass, rgba(255,255,255,0.1))',
          }}
          data-testid="dashboard-error-state"
        >
          <h3 style={{ color: '#ef4444', marginBottom: '12px', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <FiAlertTriangle /> Error de Conexión
          </h3>
          <p style={{ color: 'var(--text-secondary, #94a3b8)', marginBottom: '20px' }}>{error}</p>
          <button className="btn-primary" onClick={() => traerDatos()} data-testid="btn-retry-dashboard">
            Reintentar Conexión
          </button>
        </div>
      ) : (
        <div className="dashboard-container" data-testid="dashboard-content">
          {/* Columna Izquierda */}
          <div className="home-left-col">
            {/* Card 1: Balance Total */}
            <div className="dashboard-card" data-testid="balance-total-card">
              <h3 className="card-title">Balance Total</h3>
              <div className="balance-amount" data-testid="balance-total-value">
                {totalBalance.toLocaleString('es-ES', { minimumFractionDigits: 2 })} €
              </div>
            </div>

            {/* Card 2: Top Carteras */}
            <div className="dashboard-card" data-testid="top-carteras-card">
              <h3 className="card-title">Top 3 Carteras</h3>
              <div className="top-carteras-list">
                {topCarteras.length === 0 ? (
                  <p style={{ color: 'var(--text-secondary, #94a3b8)', fontSize: '0.9rem', marginTop: '8px' }}>
                    No hay carteras registradas.
                  </p>
                ) : (
                  topCarteras.map((c) => {
                    const porcentaje =
                      totalBalance > 0
                        ? ((c.totalCartera / totalBalance) * 100).toFixed(3)
                        : '0.000';
                    return (
                      <div key={c.idCartera} className="top-cartera-item" data-testid={`cartera-item-${c.idCartera}`}>
                        <div className="top-cartera-info">
                          <span className="top-cartera-name">{c.nombreCartera}</span>
                          <span className="top-cartera-value">
                            {c.totalCartera.toLocaleString('es-ES', { minimumFractionDigits: 2 })} €
                          </span>
                        </div>
                        <div className="top-cartera-percent">{porcentaje}%</div>
                      </div>
                    );
                  })
                )}
              </div>
            </div>
          </div>

          {/* Columna Derecha */}
          <div className="home-right-col">
            {/* Card 3: Últimas Transacciones */}
            <div className="dashboard-card transactions-card" data-testid="transactions-card">
              <h3 className="card-title">Últimas Transacciones</h3>
              <div className="table-responsive">
                <table className="transactions-table">
                  <thead>
                    <tr>
                      <th>Origen</th>
                      <th>Activo</th>
                      <th>Cantidad</th>
                      <th>Destino</th>
                      <th>Activo Recibido</th>
                      <th>Cant. Recibida</th>
                    </tr>
                  </thead>
                  <tbody>
                    {ultimosMovimientos.length === 0 ? (
                      <tr>
                        <td colSpan={6} style={{ textAlign: 'center', color: 'var(--text-secondary, #94a3b8)' }}>
                          No hay movimientos registrados.
                        </td>
                      </tr>
                    ) : (
                      ultimosMovimientos.map((m, idx) => (
                        <tr key={m.idMovimiento ?? m.id ?? idx}>
                          <td>{m.carteraOrigen}</td>
                          <td>{m.activo}</td>
                          <td>{m.enviado}</td>
                          <td>{m.carteraDestino}</td>
                          <td>{m.activoRecibido}</td>
                          <td>{m.recibido}</td>
                        </tr>
                      ))
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      )}

      <ModalTransferencia
        isOpen={modalTransferenciaAbierto}
        onClose={() => setModalTransferenciaAbierto(false)}
        onSuccess={() => traerDatos()}
      />

      <Footer />
    </main>
  );
}
