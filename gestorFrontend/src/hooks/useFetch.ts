import { useState, useEffect, useCallback, useRef } from 'react';
import api from '../api/client';
import axios, { AxiosRequestConfig, CanceledError } from 'axios';

interface UseFetchOptions extends AxiosRequestConfig {
  autoFetch?: boolean;
}

export interface UseFetchState<T> {
  data: T | null;
  loading: boolean;
  error: string | null;
}

export function useFetch<T>(url: string, options: UseFetchOptions = {}) {
  const { autoFetch = true, ...axiosConfig } = options;
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState<boolean>(autoFetch);
  const [error, setError] = useState<string | null>(null);

  // Store axiosConfig in ref to avoid unnecessary re-triggers
  const configRef = useRef(axiosConfig);
  configRef.current = axiosConfig;

  const abortControllerRef = useRef<AbortController | null>(null);

  const execute = useCallback(
    async (overrideUrl?: string, overrideConfig?: AxiosRequestConfig): Promise<T | null> => {
      // Abort previous pending request if any
      if (abortControllerRef.current) {
        abortControllerRef.current.abort();
      }

      const controller = new AbortController();
      abortControllerRef.current = controller;

      setLoading(true);
      setError(null);

      try {
        const targetUrl = overrideUrl || url;
        const response = await api.get<T>(targetUrl, {
          ...configRef.current,
          ...overrideConfig,
          signal: controller.signal,
        });

        setData(response.data);
        return response.data;
      } catch (err: unknown) {
        if (axios.isCancel(err) || err instanceof CanceledError) {
          // Request was cancelled cleanly, no error state update needed
          return null;
        }

        let message = 'Error de conexión con el servidor.';
        if (axios.isAxiosError(err)) {
          message =
            err.response?.data?.mensaje ||
            err.response?.data?.message ||
            err.message ||
            'Error al comunicarse con el servidor.';
        } else if (err instanceof Error) {
          message = err.message;
        }

        setError(message);
        return null;
      } finally {
        setLoading(false);
      }
    },
    [url]
  );

  useEffect(() => {
    if (autoFetch) {
      execute();
    }

    return () => {
      if (abortControllerRef.current) {
        abortControllerRef.current.abort();
      }
    };
  }, [execute, autoFetch]);

  return {
    data,
    loading,
    error,
    refetch: execute,
    setData,
  };
}
