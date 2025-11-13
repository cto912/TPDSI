import { useState, useEffect } from 'react';

// Iconos SVG simples
const MenuIcon = () => (
  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <line x1="3" y1="12" x2="21" y2="12"></line>
    <line x1="3" y1="6" x2="21" y2="6"></line>
    <line x1="3" y1="18" x2="21" y2="18"></line>
  </svg>
);

const LogOutIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
    <polyline points="16 17 21 12 16 7"></polyline>
    <line x1="21" y1="12" x2="9" y2="12"></line>
  </svg>
);

const FileTextIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
    <polyline points="14 2 14 8 20 8"></polyline>
    <line x1="16" y1="13" x2="8" y2="13"></line>
    <line x1="16" y1="17" x2="8" y2="17"></line>
    <polyline points="10 9 9 9 8 9"></polyline>
  </svg>
);

const HomeIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
    <polyline points="9 22 9 12 15 12 15 22"></polyline>
  </svg>
);

const BarChartIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <line x1="12" y1="20" x2="12" y2="10"></line>
    <line x1="18" y1="20" x2="18" y2="4"></line>
    <line x1="6" y1="20" x2="6" y2="16"></line>
  </svg>
);

const SettingsIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <circle cx="12" cy="12" r="3"></circle>
    <path d="M12 1v6m0 6v6m0-12l-4.5 7.8m9 0L12 7m0 10l-4.5-7.8m9 0L12 17"></path>
  </svg>
);

const ArrowLeftIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
    <line x1="19" y1="12" x2="5" y2="12"></line>
    <polyline points="12 19 5 12 12 5"></polyline>
  </svg>
);

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [currentView, setCurrentView] = useState('home');
  const [menuOpen, setMenuOpen] = useState(true);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [eventos, setEventos] = useState([]);
  const [error, setError] = useState('');
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [showEditForm, setShowEditForm] = useState(false);
  const [datosSismicos, setDatosSismicos] = useState({
    alcance: '',
    clasificacion: '',
    origen: '',
    magnitud: '',
    seriesTemporales: ''
  });
  const [loadingDatos, setLoadingDatos] = useState(false);
  const [opciones, setOpciones] = useState({
    alcance: [],
    clasificacion: [],
    origen: [],
  });
  const [decision, setDecision] = useState('');
  const [decisionError, setDecisionError] = useState('');
  const [seriesTemporales, setSeriesTemporales] = useState([]);
  const [isEditingDatos, setIsEditingDatos] = useState(false);
  const [magnitudError, setMagnitudError] = useState('');
  const [isProcessingEvent, setIsProcessingEvent] = useState(false);

  const hiddenColumns = ['id'];

  useEffect(() => {
    if (isAuthenticated && currentView === 'revision') {
      setEventos([]);
      setSelectedEvent(null);
      setShowEditForm(false);
      setDatosSismicos({ alcance: '', clasificacion: '', origen: ''});
      setSeriesTemporales([]);

      fetch("http://localhost:8080/api/pantalla/getESRev")
        .then((res) => {
          if (!res.ok) throw new Error("Error al obtener los eventos sísmicos");
          return res.json();
        })
        .then((data) => setEventos(data))
        .catch((err) => console.error(err));
    }
  }, [isAuthenticated, currentView]);

  const handleLogin = () => {
    if (username && password) {
      fetch("http://localhost:8080/api/pantalla/iniciarSesion", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userName: username,
          password: password,
        }),
      })
      .then((res) => {
        if(!res.ok){
          setError('Usuario y/o contraseña incorrectos')
        } else{
          setIsAuthenticated(true);
          setError('');
        }
      })
      .catch(() => {
        setError("Error al conectar con el servidor");
      });
    } else {
      setError('Por favor ingrese usuario y contraseña');
    }
  };

  const handleLogout = () => {

    fetch("http://localhost:8080/api/pantalla/cerrarSesion", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userName: username,
        password: password,
      }),
    })
    .then((res) => {
      if(res.ok){
        setIsAuthenticated(false);
        setUsername('');
        setPassword('');
        setCurrentView('home');
        setSelectedEvent(null);
        setShowEditForm(false);
        setDatosSismicos({ alcance: '', clasificacion: '', origen: ''});
      }
    })
    .catch(() => {
      setError("Error al conectar con el servidor");
    });
  };

  const handleConfirmSelection = async () => {
    if (selectedEvent) {
      console.log('ID del evento seleccionado:', selectedEvent.id);
      setLoadingDatos(true);
      setIsProcessingEvent(true);

      try {
        await fetch('http://localhost:8080/api/pantalla/postESRev', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ id: selectedEvent.id })
        });

        const [datosRes, alcanceRes, clasifRes, origenRes] = await Promise.all([
          fetch('http://localhost:8080/api/pantalla/getDatosSismicos'),
          fetch('http://localhost:8080/api/pantalla/getAllAlcance'),
          fetch('http://localhost:8080/api/pantalla/getAllClasificacion'),
          fetch('http://localhost:8080/api/pantalla/getAllOrigenDeGeneracion'),
        ]);

        if (!datosRes.ok) throw new Error('Error al obtener datos sísmicos');
        if (!alcanceRes.ok) throw new Error('Error al obtener alcance');
        if (!clasifRes.ok) throw new Error('Error al obtener clasificación');
        if (!origenRes.ok) throw new Error('Error al obtener origen');

        const data = await datosRes.json();
        const dataAlcance = await alcanceRes.json();
        const dataClasificacion = await clasifRes.json();
        const dataOrigen = await origenRes.json();


        // Normalizamos la respuesta para asegurarnos que decision siempre exista
        setDatosSismicos({
          alcance: data.alcance ?? '',
          clasificacion: data.clasificacion ?? '',
          origen: data.origen ?? '',
          magnitud: selectedEvent.valorMagnitud
        });

        setOpciones({
          alcance: dataAlcance,
          clasificacion: dataClasificacion,
          origen: dataOrigen
        });

        setSeriesTemporales(data.seriesTemporales || []);

        setShowEditForm(true);
        setIsEditingDatos(false);
      } catch (error) {
        console.error('Error:', error);
        alert('Error al procesar la solicitud');
        setIsProcessingEvent(false);
      } finally {
        setLoadingDatos(false);
      }

    }
  };

  const recargarEventos = () => {
    fetch("http://localhost:8080/api/pantalla/getESRev")
    .then((res) => {
      if (!res.ok) throw new Error("Error al obtener los eventos sísmicos");
      return res.json();
    })
    .then((data) => setEventos(data))
    .catch((err) => console.error(err));
  };

  const handleSaveDatos = async () => {
    // Validación en frontend: decision obligatoria
    if (!decision) {
      setDecisionError('Debes seleccionar una decisión antes de guardar.');
      return;
    }
    setDecisionError('');

    if (!datosSismicos.magnitud || datosSismicos.magnitud === null || datosSismicos.magnitud === undefined) {
    setMagnitudError('La magnitud es obligatoria.');
    return;
    }
    
    const magnitudNum = parseFloat(datosSismicos.magnitud);
    if (isNaN(magnitudNum)) {
      setMagnitudError('La magnitud debe ser un número válido.');
      return;
    }
    
    if (magnitudNum < 0) {
      setMagnitudError('La magnitud debe ser mayor o igual a 0.');
      return;
    }
    setMagnitudError('');

    if(decision === "rechazar"){
      try {
          const res = await fetch('http://localhost:8080/api/pantalla/rechazarEvento', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(datosSismicos)
          });

          if (!res.ok) {
            const text = await res.text();
            throw new Error(`Error backend: ${res.status} ${text}`);
          }

          alert('Datos guardados correctamente');
          setShowEditForm(false);
          setSelectedEvent(null);
          setDatosSismicos({ alcance: '', clasificacion: '', origen: ''});
          setIsEditingDatos(false)
          recargarEventos();
          setIsProcessingEvent(false);
        } catch (err) {
          console.error(err);
          alert('Error al guardar los datos: ' + err.message);
        }
    }
    if(decision === "confirmar"){
      try {
          const res = await fetch('http://localhost:8080/api/pantalla/confirmarEvento', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(datosSismicos)
          });

          if (!res.ok) {
            const text = await res.text();
            throw new Error(`Error backend: ${res.status} ${text}`);
          }

          alert('Datos guardados correctamente');
          setShowEditForm(false);
          setSelectedEvent(null);
          setDatosSismicos({ alcance: '', clasificacion: '', origen: ''});
          setIsEditingDatos(false)
          recargarEventos();
          setIsProcessingEvent(false);
        } catch (err) {
          console.error(err);
          alert('Error al guardar los datos: ' + err.message);
        }
    }
    if(decision === "solicitar_revision"){
      try {
          const res = await fetch('http://localhost:8080/api/pantalla/derivarEvento', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(datosSismicos)
          });

          if (!res.ok) {
            const text = await res.text();
            throw new Error(`Error backend: ${res.status} ${text}`);
          }

          alert('Datos guardados correctamente');
          setShowEditForm(false);
          setSelectedEvent(null);
          setDatosSismicos({ alcance: '', clasificacion: '', origen: ''});
          setIsEditingDatos(false)
          recargarEventos();
          setIsProcessingEvent(false);
        } catch (err) {
          console.error(err);
          alert('Error al guardar los datos: ' + err.message);
        }
    }
  };

  const handleCancelEdit = async () => {

    try{
      await fetch('http://localhost:8080/api/pantalla/cancelar', {
        method: 'DELETE',
      })

      setShowEditForm(false);
      setSelectedEvent(null);
      setDatosSismicos({ alcance: '', clasificacion: '', origen: ''});
      setIsProcessingEvent(false);
      recargarEventos();
    } catch(error){
      console.error('Error al cancelar:', error);
      alert('Error al cancelar la operacion')
    }

  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleLogin();
    }
  };

  if (!isAuthenticated) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-slate-900 flex items-center justify-center p-4">
        <div className="bg-white rounded-2xl shadow-2xl p-8 w-full max-w-md">
          <div className="text-center mb-8">
            <div className="bg-blue-600 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <BarChartIcon />
            </div>
            <h1 className="text-3xl font-bold text-gray-800 mb-2">Sistema Sísmico</h1>
            <p className="text-gray-600">Inicie sesión para continuar</p>
          </div>

          <div className="space-y-6">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Usuario
              </label>
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                onKeyPress={handleKeyPress}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                placeholder="Ingrese su usuario"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Contraseña
              </label>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                onKeyPress={handleKeyPress}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                placeholder="Ingrese su contraseña"
              />
            </div>

            {error && (
              <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
                {error}
              </div>
            )}

            <button
              onClick={handleLogin}
              className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-lg transition duration-200 shadow-lg hover:shadow-xl"
            >
              Iniciar Sesión
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <div className={`${menuOpen ? 'w-64' : 'w-20'} bg-slate-800 text-white transition-all duration-300 flex flex-col`}>
        <div className="p-4 flex items-center justify-between border-b border-slate-700">
          <h2 className={`font-bold text-xl ${!menuOpen && 'hidden'}`}>Sistema Sísmico</h2>
          <button
            onClick={() => setMenuOpen(!menuOpen)}
            className="p-2 hover:bg-slate-700 rounded-lg transition"
          >
            <MenuIcon />
          </button>
        </div>

        <nav className="flex-1 p-4 space-y-2">
          <button
            onClick={() => !isProcessingEvent && setCurrentView('home')}
            disabled={isProcessingEvent}
            className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg transition ${
              currentView === 'home' ? 'bg-blue-600' : 'hover:bg-slate-700'
            } ${isProcessingEvent ? 'opacity-50 cursor-not-allowed' : ''}`}
          >
            <HomeIcon />
            <span className={!menuOpen && 'hidden'}>Inicio</span>
          </button>

          <button
            onClick={() => !isProcessingEvent && setCurrentView('revision')}
            disabled={isProcessingEvent}
            className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg transition ${
              currentView === 'revision' ? 'bg-blue-600' : 'hover:bg-slate-700'
            } ${isProcessingEvent ? 'opacity-50 cursor-not-allowed' : ''}`}
          >
            <FileTextIcon />
            <span className={!menuOpen && 'hidden'}>Registrar Revisión Manual</span>
          </button>

          <button
            onClick={() => !isProcessingEvent && setCurrentView('reportes')}
            disabled={isProcessingEvent}
            className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg transition ${
              currentView === 'reportes' ? 'bg-blue-600' : 'hover:bg-slate-700'
            } ${isProcessingEvent ? 'opacity-50 cursor-not-allowed' : ''}`}
          >
            <BarChartIcon />
            <span className={!menuOpen && 'hidden'}>Reportes</span>
          </button>

          <button
            onClick={() => !isProcessingEvent && setCurrentView('configuracion')}
            disabled={isProcessingEvent}
            className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg transition ${
              currentView === 'configuracion' ? 'bg-blue-600' : 'hover:bg-slate-700'
            } ${isProcessingEvent ? 'opacity-50 cursor-not-allowed' : ''}`}
          >
            <SettingsIcon />
            <span className={!menuOpen && 'hidden'}>Configuración</span>
          </button>
        </nav>

        <div className="p-4 border-t border-slate-700">
          <button
            onClick={() => !isProcessingEvent && handleLogout()}
            disabled={isProcessingEvent}
            className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg transition ${
              isProcessingEvent 
              ? 'opacity-50 cursor-not-allowed bg-slate-700' 
              : 'hover:bg-red-600'
            }`}
          >
            <LogOutIcon />
            <span className={!menuOpen && 'hidden'}>Cerrar Sesión</span>
          </button>
        </div>
      </div>

      {/* Main Content */}
      <div className="flex-1 overflow-auto">
        <div className="p-8">
          {currentView === 'home' && (
            <div>
              <h1 className="text-4xl font-bold text-gray-800 mb-4">Bienvenido al Sistema</h1>
              <p className="text-gray-600 mb-8">Seleccione una opción del menú para comenzar</p>
              
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                <div className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl transition">
                  <div className="bg-blue-100 w-12 h-12 rounded-lg flex items-center justify-center mb-4">
                    <FileTextIcon />
                  </div>
                  <h3 className="text-xl font-semibold mb-2">Revisiones</h3>
                  <p className="text-gray-600">Registre y consulte revisiones manuales</p>
                </div>

                <div className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl transition">
                  <div className="bg-green-100 w-12 h-12 rounded-lg flex items-center justify-center mb-4">
                    <BarChartIcon />
                  </div>
                  <h3 className="text-xl font-semibold mb-2">Reportes</h3>
                  <p className="text-gray-600">Visualice estadísticas y análisis</p>
                </div>

                <div className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl transition">
                  <div className="bg-purple-100 w-12 h-12 rounded-lg flex items-center justify-center mb-4">
                    <SettingsIcon />
                  </div>
                  <h3 className="text-xl font-semibold mb-2">Configuración</h3>
                  <p className="text-gray-600">Ajuste las preferencias del sistema</p>
                </div>
              </div>
            </div>
          )}

          {currentView === 'revision' && (
            <div>
              <h1 className="text-4xl font-bold text-gray-800 mb-6">Eventos Sísmicos - Revisión Manual</h1>
              
              {showEditForm && datosSismicos ? (
                <div className="bg-white rounded-xl shadow-lg p-8">
                  <button
                    onClick={handleCancelEdit}
                    className="flex items-center gap-2 text-blue-600 hover:text-blue-700 mb-6 font-semibold"
                  >
                    <ArrowLeftIcon />
                    Volver a la lista
                  </button>

                  <div className="mb-6 p-4 bg-blue-50 rounded-lg">
                    <h3 className="font-semibold text-blue-900 mb-2">Evento Seleccionado</h3>
                    <div className="text-sm text-blue-700 space-y-1">
                      <p><strong>ID:</strong> {selectedEvent.id}</p>
                      <p><strong>Fecha:</strong> {selectedEvent.fechaHoraOcurrencia 
                        ? new Date(selectedEvent.fechaHoraOcurrencia).toLocaleString() 
                        : 'N/A'}</p>
                      {selectedEvent.magnitud && <p><strong>Magnitud:</strong> {selectedEvent.magnitud}</p>}
                      {selectedEvent.profundidad && <p><strong>Profundidad:</strong> {selectedEvent.profundidad}</p>}
                    </div>
                  </div>

                  <h2 className="text-2xl font-bold text-gray-800 mb-6">Datos Sísmicos</h2>

                  <div className="space-y-6">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Series Temporales
                      </label>
                      {seriesTemporales && seriesTemporales.length > 0 ? (
                        <div className="space-y-4">
                          {seriesTemporales.map((serie) => (
                            <div key={serie.id} className="border border-gray-300 rounded-lg p-4 bg-gray-50">
                              <div className="grid grid-cols-2 gap-4 mb-4">
                                <div>
                                  <p className="text-sm text-gray-600">Código Estación:</p>
                                  <p className="font-semibold">{serie.codigoEstacion}</p>
                                </div>
                                <div>
                                  <p className="text-sm text-gray-600">Frecuencia de Muestreo:</p>
                                  <p className="font-semibold">{serie.frecuenciaMuestreo} Hz</p>
                                </div>
                                <div>
                                  <p className="text-sm text-gray-600">Fecha de Registro:</p>
                                  <p className="font-semibold">
                                    {new Date(serie.fechaHoraRegistro).toLocaleString()}
                                  </p>
                                </div>
                                <div>
                                  <p className="text-sm text-gray-600">Condición de Alarma:</p>
                                  <p className={`font-semibold ${serie.condicionAlarma ? 'text-red-600' : 'text-green-600'}`}>
                                    {serie.condicionAlarma ? 'ACTIVA' : 'NORMAL'}
                                  </p>
                                </div>
                              </div>

                              {serie.muestras && serie.muestras.length > 0 && (
                                <div>
                                  <p className="text-sm font-medium text-gray-700 mb-2">Muestras:</p>
                                  <div className="overflow-x-auto">
                                    <table className="w-full text-sm">
                                      <thead className="bg-gray-200">
                                        <tr>
                                          <th className="px-3 py-2 text-left">Fecha/Hora</th>
                                          <th className="px-3 py-2 text-left">Velocidad de onda</th>
                                          <th className="px-3 py-2 text-left">Frecuencia de onda</th>
                                          <th className="px-3 py-2 text-left">Longitud</th>
                                        </tr>
                                      </thead>
                                      <tbody className="divide-y divide-gray-200 bg-white">
                                        {serie.muestras.map((muestra, idx) => (
                                          <tr key={idx}>
                                            <td className="px-3 py-2">
                                              {new Date(muestra[0]).toLocaleString()}
                                            </td>
                                            <td className="px-3 py-2">{muestra[1][0]}</td>
                                            <td className="px-3 py-2">{muestra[2][0]}</td>
                                            <td className="px-3 py-2">{muestra[3][0]}</td>
                                          </tr>
                                        ))}
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
                              )}
                            </div>
                          ))}
                        </div>
                      ) : (
                        <p className="text-gray-500 text-sm">No hay series temporales disponibles</p>
                      )}
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Magnitud <span className="text-red-500">*</span>
                      </label>
                      {isEditingDatos ? (
                        <div>
                          <input
                            type="number"
                            step="0.01"
                            min="0"
                            value={datosSismicos.magnitud}
                            onChange={(e) => setDatosSismicos({...datosSismicos, magnitud: e.target.value})}
                            className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                            placeholder="Ingrese un valor mayor o igual a 0"
                          />
                          {magnitudError && (
                            <p className="text-sm text-red-600 mt-2">{magnitudError}</p>
                          )}
                        </div>
                      ) : (
                        <div className="w-full px-4 py-3 border border-gray-200 rounded-lg bg-gray-50">
                          <p className="text-gray-700">{datosSismicos.magnitud || 'No especificado'}</p>
                        </div>
                      )}
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Alcance
                      </label>
                      {isEditingDatos ? (
                        <select
                          value={datosSismicos.alcance}
                          onChange={(e) => setDatosSismicos({...datosSismicos, alcance: e.target.value})}
                          className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                        >
                          {opciones.alcance && opciones.alcance.length > 0 ? (
                            opciones.alcance.map((opcion) => (
                              <option key={opcion.id} value={opcion.nombre}>{opcion.nombre}</option>
                            ))
                          ) : (
                            <option value="">Cargando...</option>
                          )}
                        </select>
                      ) : (
                          <div className="w-full px-4 py-3 border border-gray-200 rounded-lg bg-gray-50">
                            <p className="text-gray-700">{datosSismicos.alcance || 'No especificado'}</p>
                          </div>
                        )}
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Clasificación
                      </label>
                      <p className="w-full px-4 py-3 border border-gray-200 rounded-lg bg-gray-50">{datosSismicos.clasificacion}</p>
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Origen
                      </label>
                      {isEditingDatos ? (
                        <select
                          value={datosSismicos.origen}
                          onChange={(e) => setDatosSismicos({...datosSismicos, origen: e.target.value})}
                          className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none"
                        >
                          {opciones.origen && opciones.origen.length > 0 ? (
                            opciones.origen.map((opcion) => (
                              <option key={opcion.id} value={opcion.nombre}>{opcion.nombre}</option>
                            ))
                          ) : (
                            <option value="">Cargando...</option>
                          )}
                        </select>
                      ) : (
                          <div className="w-full px-4 py-3 border border-gray-200 rounded-lg bg-gray-50">
                            <p className="text-gray-700">{datosSismicos.origen || 'No especificado'}</p>
                          </div>
                      )}
                    </div>

                    {!isEditingDatos && (
                      <div className="pt-2">
                        <button
                          onClick={() => setIsEditingDatos(true)}
                          className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-lg font-semibold transition"
                        >
                          Editar Datos
                        </button>
                      </div>
                    )}

                    {/* Sección de decisión (radios) */}
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Acción sobre el evento <span className="text-red-500">*</span>
                      </label>

                      <div className="flex flex-col gap-2">
                        <label className="inline-flex items-center">
                          <input
                            type="radio"
                            name="decision"
                            value="confirmar"
                            checked={decision === "confirmar"}
                            onChange={(e) => setDecision(e.target.value)}
                            className="form-radio h-5 w-5"
                          />
                          <span className="ml-2">Confirmar evento</span>
                        </label>

                        <label className="inline-flex items-center">
                          <input
                            type="radio"
                            name="decision"
                            value="rechazar"
                            checked={decision === "rechazar"}
                            onChange={(e) => setDecision(e.target.value)}
                            className="form-radio h-5 w-5"
                          />
                          <span className="ml-2">Rechazar evento</span>
                        </label>

                        <label className="inline-flex items-center">
                          <input
                            type="radio"
                            name="decision"
                            value="solicitar_revision"
                            checked={decision === "solicitar_revision"}
                            onChange={(e) => setDecision(e.target.value)}
                            className="form-radio h-5 w-5"
                          />
                          <span className="ml-2">Solicitar revisión a experto</span>
                        </label>
                      </div>

                      {decisionError && (
                        <p className="text-sm text-red-600 mt-2">{decisionError}</p>
                      )}
                    </div>

                    <div className="flex gap-4 pt-4">
                      <button
                        onClick={handleSaveDatos}
                        className="flex-1 bg-green-600 hover:bg-green-700 text-white py-3 rounded-lg font-semibold transition"
                      >
                        Guardar Cambios
                      </button>
                      <button
                        onClick={handleCancelEdit}
                        className="flex-1 bg-gray-500 hover:bg-gray-600 text-white py-3 rounded-lg font-semibold transition"
                      >
                        Cancelar
                      </button>
                    </div>
                  </div>
                </div>
              ) : (
                <>
                  {!eventos || eventos.length === 0 ? (
                    <div className="bg-white p-8 rounded-xl shadow-lg text-center">
                      <p className="text-gray-600">No hay datos disponibles.</p>
                    </div>
                  ) : (
                    <div>
                      {selectedEvent && (
                        <div className="bg-blue-50 border-2 border-blue-500 rounded-xl p-4 mb-4 flex items-center justify-between">
                          <div>
                            <p className="font-semibold text-blue-900">Evento seleccionado</p>
                            <p className="text-sm text-blue-700">
                              {selectedEvent.fechaHoraOcurrencia 
                                ? new Date(selectedEvent.fechaHoraOcurrencia).toLocaleString() 
                                : 'Fecha no disponible'}
                            </p>
                          </div>
                          <div className="flex gap-2">
                            <button
                              onClick={handleConfirmSelection}
                              disabled={loadingDatos}
                              className="bg-green-600 hover:bg-green-700 disabled:bg-gray-400 text-white px-6 py-2 rounded-lg font-semibold transition"
                            >
                              {loadingDatos ? 'Cargando...' : 'Confirmar Selección'}
                            </button>
                            <button
                              onClick={() => setSelectedEvent(null)}
                              className="bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded-lg transition"
                            >
                              Cancelar
                            </button>
                          </div>
                        </div>
                      )}

                      <div className="bg-white rounded-xl shadow-lg overflow-hidden">
                        <div className="overflow-x-auto">
                          <table className="w-full">
                            <thead className="bg-slate-800 text-white">
                              <tr>
                                <th className="px-6 py-4 text-left font-semibold">Seleccionar</th>
                                {Object.keys(eventos[0])
                                  .filter(header => !hiddenColumns.includes(header))
                                  .map((header) => (
                                    <th key={header} className="px-6 py-4 text-left font-semibold">
                                      {header}
                                    </th>
                                  ))}
                              </tr>
                            </thead>
                            <tbody className="divide-y divide-gray-200">
                              {eventos.map((item, i) => (
                                <tr 
                                  key={i} 
                                  className={`transition cursor-pointer ${
                                    selectedEvent === item 
                                      ? 'bg-blue-100 hover:bg-blue-200' 
                                      : 'hover:bg-gray-50'
                                  }`}
                                  onClick={() => setSelectedEvent(item)}
                                >
                                  <td className="px-6 py-4">
                                    <input
                                      type="radio"
                                      checked={selectedEvent === item}
                                      onChange={() => setSelectedEvent(item)}
                                      className="w-4 h-4 cursor-pointer"
                                    />
                                  </td>
                                  {Object.keys(eventos[0])
                                    .filter(header => !hiddenColumns.includes(header))
                                    .map((header) => (
                                      <td key={header} className="px-6 py-4 text-gray-700">
                                        {header === "fechaHoraOcurrencia"
                                          ? new Date(item[header]).toLocaleString()
                                          : item[header]}
                                      </td>
                                    ))}
                                </tr>
                              ))}
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </div>
                  )}
                </>
              )}
            </div>
          )}

          {currentView === 'reportes' && (
            <div>
              <h1 className="text-4xl font-bold text-gray-800 mb-6">Reportes y Estadísticas</h1>
              <div className="bg-white p-8 rounded-xl shadow-lg">
                <p className="text-gray-600">Sección de reportes en desarrollo...</p>
              </div>
            </div>
          )}

          {currentView === 'configuracion' && (
            <div>
              <h1 className="text-4xl font-bold text-gray-800 mb-6">Configuración</h1>
              <div className="bg-white p-8 rounded-xl shadow-lg">
                <p className="text-gray-600">Sección de configuración en desarrollo...</p>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default App;
