import React, { useState, useRef, useEffect } from 'react';
import { FiChevronDown, FiCheck } from 'react-icons/fi';
import './CustomSelect.css';

export interface SelectOptionItem {
  value: string | number;
  label?: string | number;
  disabled?: boolean;
}

export type SelectOption = SelectOptionItem | string | number;

export interface CustomSelectProps {
  options?: SelectOption[];
  value?: string | number;
  onChange: (value: any) => void;
  placeholder?: string;
  disabled?: boolean;
  className?: string;
  id?: string;
}

export default function CustomSelect({
  options = [],
  value,
  onChange,
  placeholder = '-- Seleccionar --',
  disabled = false,
  className = '',
  id,
}: CustomSelectProps): React.JSX.Element {
  const [isOpen, setIsOpen] = useState(false);
  const containerRef = useRef<HTMLDivElement | null>(null);

  // Normalizar opciones a objetos { value, label, disabled }
  const normalizedOptions: SelectOptionItem[] = options.map((opt) => {
    if (typeof opt === 'object' && opt !== null) {
      return {
        value: opt.value,
        label: opt.label !== undefined ? opt.label : opt.value,
        disabled: opt.disabled || false,
      };
    }
    return { value: opt, label: opt, disabled: false };
  });

  const selectedOption = normalizedOptions.find(
    (opt) => String(opt.value) === String(value)
  );

  useEffect(() => {
    const handleOutsideClick = (e: MouseEvent) => {
      if (containerRef.current && !containerRef.current.contains(e.target as Node)) {
        setIsOpen(false);
      }
    };

    if (isOpen) {
      document.addEventListener('mousedown', handleOutsideClick);
    }
    return () => {
      document.removeEventListener('mousedown', handleOutsideClick);
    };
  }, [isOpen]);

  const handleSelect = (optValue: string | number, optDisabled?: boolean) => {
    if (optDisabled) return;
    onChange(optValue);
    setIsOpen(false);
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (disabled) return;
    if (e.key === 'Enter' || e.key === ' ') {
      e.preventDefault();
      setIsOpen((prev) => !prev);
    } else if (e.key === 'Escape') {
      setIsOpen(false);
    }
  };

  return (
    <div
      className={`custom-select-container ${className} ${disabled ? 'disabled' : ''} ${isOpen ? 'open' : ''}`}
      ref={containerRef}
      id={id}
    >
      <button
        type="button"
        className="custom-select-trigger"
        onClick={() => !disabled && setIsOpen((prev) => !prev)}
        onKeyDown={handleKeyDown}
        disabled={disabled}
        aria-haspopup="listbox"
        aria-expanded={isOpen}
      >
        <span className={`trigger-label ${!selectedOption ? 'placeholder' : ''}`}>
          {selectedOption ? selectedOption.label : placeholder}
        </span>
        <FiChevronDown className={`trigger-icon ${isOpen ? 'rotate' : ''}`} />
      </button>

      {isOpen && (
        <div className="custom-select-dropdown" role="listbox">
          {normalizedOptions.length === 0 ? (
            <div className="custom-select-empty">No hay opciones disponibles</div>
          ) : (
            normalizedOptions.map((opt) => {
              const isSelected = String(opt.value) === String(value);
              return (
                <div
                  key={String(opt.value)}
                  className={`custom-select-option ${isSelected ? 'selected' : ''} ${opt.disabled ? 'option-disabled' : ''}`}
                  onClick={() => handleSelect(opt.value, opt.disabled)}
                  role="option"
                  aria-selected={isSelected}
                >
                  <span className="option-label">{opt.label}</span>
                  {isSelected && <FiCheck className="option-check" />}
                </div>
              );
            })
          )}
        </div>
      )}
    </div>
  );
}
