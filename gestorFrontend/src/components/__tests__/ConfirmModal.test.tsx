import { describe, it, expect, vi } from 'vitest';
import { render, screen, fireEvent } from '@testing-library/react';
import ConfirmModal from '../ConfirmModal';

describe('ConfirmModal Component', () => {
  it('does not render when isOpen is false', () => {
    render(
      <ConfirmModal
        isOpen={false}
        title="¿Eliminar elemento?"
        message="Esta acción no se puede deshacer."
        onConfirm={vi.fn()}
        onCancel={vi.fn()}
      />
    );

    expect(screen.queryByRole('dialog')).not.toBeInTheDocument();
  });

  it('renders title and message correctly when isOpen is true', () => {
    render(
      <ConfirmModal
        isOpen={true}
        title="¿Eliminar Cartera?"
        message="Esta acción eliminará la cartera definitivamente."
        confirmText="Sí, eliminar"
        cancelText="Volver"
        onConfirm={vi.fn()}
        onCancel={vi.fn()}
      />
    );

    expect(screen.getByRole('dialog')).toBeInTheDocument();
    expect(screen.getByText('¿Eliminar Cartera?')).toBeInTheDocument();
    expect(screen.getByText('Esta acción eliminará la cartera definitivamente.')).toBeInTheDocument();
    expect(screen.getByText('Sí, eliminar')).toBeInTheDocument();
    expect(screen.getByText('Volver')).toBeInTheDocument();
  });

  it('triggers onConfirm when confirm button is clicked', () => {
    const handleConfirm = vi.fn();
    render(
      <ConfirmModal
        isOpen={true}
        title="Confirmar"
        message="¿Proceder?"
        onConfirm={handleConfirm}
        onCancel={vi.fn()}
      />
    );

    fireEvent.click(screen.getByRole('button', { name: 'Confirmar' }));
    expect(handleConfirm).toHaveBeenCalledTimes(1);
  });

  it('triggers onCancel when cancel button is clicked', () => {
    const handleCancel = vi.fn();
    render(
      <ConfirmModal
        isOpen={true}
        title="Confirmar"
        message="¿Proceder?"
        onConfirm={vi.fn()}
        onCancel={handleCancel}
      />
    );

    fireEvent.click(screen.getByText('Cancelar'));
    expect(handleCancel).toHaveBeenCalledTimes(1);
  });

  it('disables buttons and shows loading spinner when loading is true', () => {
    render(
      <ConfirmModal
        isOpen={true}
        title="Confirmar"
        message="¿Proceder?"
        loading={true}
        onConfirm={vi.fn()}
        onCancel={vi.fn()}
      />
    );

    expect(screen.getByText('Procesando...')).toBeInTheDocument();
    expect(screen.getByText('Cancelar')).toBeDisabled();
    expect(screen.getByRole('button', { name: /procesando/i })).toBeDisabled();
  });

  it('triggers onCancel when Escape key is pressed', () => {
    const handleCancel = vi.fn();
    render(
      <ConfirmModal
        isOpen={true}
        title="Confirmar"
        message="¿Proceder?"
        onConfirm={vi.fn()}
        onCancel={handleCancel}
      />
    );

    fireEvent.keyDown(window, { key: 'Escape' });
    expect(handleCancel).toHaveBeenCalledTimes(1);
  });
});
