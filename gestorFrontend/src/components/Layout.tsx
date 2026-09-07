import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../pages/Sidebar';

export interface LayoutProps {
  sidebarOpen: boolean;
  toggleTheme: () => void;
  theme: string;
  closeSidebar: () => void;
}

export default function Layout({
  sidebarOpen,
  toggleTheme,
  theme,
  closeSidebar,
}: LayoutProps): React.JSX.Element {
  return (
    <div className="app-container">
      {/* Background Blobs for Glassmorphism backdrop */}
      <div className="bg-blobs">
        <div className="blob blob-1"></div>
        <div className="blob blob-2"></div>
        <div className="blob blob-3"></div>
      </div>

      <Sidebar
        isOpen={sidebarOpen}
        toggleTheme={toggleTheme}
        theme={theme}
        closeSidebar={closeSidebar}
      />

      <div
        className={`mobile-overlay ${sidebarOpen ? 'open' : ''}`}
        onClick={closeSidebar}
      ></div>

      <Outlet />
    </div>
  );
}
