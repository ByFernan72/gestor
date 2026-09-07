import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import Home from '../Home';
import api from '../../api/client';

// Mock api client
vi.mock('../../api/client', () => {
  return {
    default: {
      get: vi.fn(),
      interceptors: {
        response: { use: vi.fn() },
      },
    },
  };
});

describe('Home Component (Dashboard)', () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('renders loading skeleton initially', () => {
    // Return unresolved promise to simulate pending request
    vi.mocked(api.get).mockReturnValue(new Promise(() => {}));

    render(<Home />);

    expect(screen.getByTestId('dashboard-skeleton')).toBeInTheDocument();
    expect(screen.getByText('Bienvenido/a !')).toBeInTheDocument();
    expect(screen.getByTestId('btn-quick-transfer')).toBeInTheDocument();
  });

  it('renders dashboard data correctly on successful API response', async () => {
    const mockDashboardData = {
      balanceTotal: 15420.5,
      carteras: [
        { idCartera: 1, nombreCartera: 'Cartera Cripto', totalCartera: 10000 },
        { idCartera: 2, nombreCartera: 'Ahorro Fiat', totalCartera: 5420.5 },
      ],
      movimientos: [
        {
          id: 1,
          carteraOrigen: 'Ahorro Fiat',
          activo: 'EUR',
          enviado: 500,
          carteraDestino: 'Cartera Cripto',
          activoRecibido: 'BTC',
          recibido: 0.008,
        },
      ],
    };

    vi.mocked(api.get).mockResolvedValueOnce({ data: mockDashboardData });

    render(<Home />);

    await waitFor(() => {
      expect(screen.getByTestId('dashboard-content')).toBeInTheDocument();
    });

    // Check Balance Total Card
    expect(screen.getByTestId('balance-total-card')).toBeInTheDocument();
    expect(screen.getByText(/15\.420,50/)).toBeInTheDocument();

    // Check Top Carteras
    expect(screen.getByTestId('cartera-item-1')).toHaveTextContent('Cartera Cripto');
    expect(screen.getByTestId('cartera-item-2')).toHaveTextContent('Ahorro Fiat');

    // Check Transactions Table
    expect(screen.getByTestId('transactions-card')).toBeInTheDocument();
    expect(screen.getByText('EUR')).toBeInTheDocument();
    expect(screen.getByText('BTC')).toBeInTheDocument();
  });

  it('renders error state when API fails', async () => {
    vi.mocked(api.get).mockRejectedValueOnce(new Error('Network connection refused'));

    render(<Home />);

    await waitFor(() => {
      expect(screen.getByTestId('dashboard-error-state')).toBeInTheDocument();
    });

    expect(screen.getByText(/Error de Conexión/i)).toBeInTheDocument();
    expect(screen.getByTestId('btn-retry-dashboard')).toBeInTheDocument();
  });
});
