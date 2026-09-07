export type TipoActivo = 'FIAT' | 'CRYPTO' | 'ACCION' | 'MATERIA_PRIMA' | 'OTRO';

export interface CarteraTopDto {
  idCartera: number;
  nombreCartera: string;
  totalCartera: number;
}

export interface MovimientoResumenDto {
  id?: number;
  idMovimiento?: number;
  carteraOrigen: string;
  activo: string;
  enviado: number;
  carteraDestino: string;
  activoRecibido: string;
  recibido: number;
  fechaMovimiento?: string;
}

export interface DashboardResumenResponseDto {
  balanceTotal: number;
  carteras: CarteraTopDto[];
  movimientos: MovimientoResumenDto[];
}

export interface CarteraItemDto {
  idCartera: number;
  nombreCartera: string;
  descripcion?: string;
  totalCartera: number;
  totalActivosCartera: number;
}

export interface CarteraListadoResponseDto {
  carteras: CarteraItemDto[];
  paginaActual: number;
  totalPaginas: number;
  totalElementos: number;
}

export interface ActivoDetalleDto {
  idActivo?: number;
  nombreActivo: string;
  tipoActivo?: TipoActivo;
  tipo?: TipoActivo;
  balance: number;
  fechaObtencionActivo?: string;
}

export interface CarteraDetalleDto {
  idCartera: number;
  nombreCartera: string;
  descripcion?: string;
  createdAt?: string;
  activos: ActivoDetalleDto[];
}

export interface PerfilResponseDto {
  idPerfil: number;
  nombrePerfil: string;
  links: string[];
  fechaCreacionPerfil?: string;
}

export interface CrearActivoPayload {
  nombreActivo: string;
  tipo: TipoActivo;
  balance: number;
}

export interface CrearCarteraPayload {
  nombreCartera: string;
  descripcion?: string;
  activos: CrearActivoPayload[];
}

export interface MovimientoPayload {
  idCarteraOrigen: number;
  idCarteraDestino: number;
  idActivoOrigen: number;
  idActivoDestino?: number | null;
  cantidadOrigen: number;
  cantidadDestino: number;
  nombreNuevoActivo?: string | null;
  tipoNuevoActivo?: TipoActivo | null;
}

export interface ApiErrorResponse {
  mensaje?: string;
  message?: string;
  codigo?: string | number;
  timestamp?: string;
}

export interface PageResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  number: number;
  size?: number;
  first?: boolean;
  last?: boolean;
}

export interface DraftActivo {
  id: string | number;
  nombreActivo: string;
  tipo: TipoActivo;
  balance: string | number;
}

export interface ActualizarActivoPayload {
  idActivo: number;
  idCartera: number;
  nombreActivo: string;
  tipoActivo: TipoActivo;
  balance: number;
  fechaObtencionActivo?: string;
}
