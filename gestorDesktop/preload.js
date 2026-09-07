// preload.js - script de seguridad de Electron
// Por ahora no expone APIs adicionales, pero es requerido para contextIsolation
'use strict';

const { contextBridge } = require('electron');

// Exponer versiones del entorno (opcional, útil para debugging)
contextBridge.exposeInMainWorld('versions', {
  node: () => process.versions.node,
  chrome: () => process.versions.chrome,
  electron: () => process.versions.electron,
});
