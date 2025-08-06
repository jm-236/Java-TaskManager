import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import TelaLogin from './pages/login/login.jsx'
import Cadastro from './pages/cadastro/cadastro.jsx'
import MudarSenha from './pages/mudar_senha/mudar_senha.jsx'
import NovaSenha from './pages/nova_senha/nova_senha.jsx'
import TelaInicial from './pages/tela_inicial/tela_inicial.jsx'
import NovaTarefa from './pages/nova_tarefa/nova_tarefa.jsx'
import VisualizarTarefa from './pages/visualizar_tarefa/visualizar_tarefa.jsx'
import VisualizarPerfil from './pages/visualizar_perfil/visualizar_perfil.jsx'
import RotaProtegida from './components/RotaProtegida.jsx'
import { Navigate } from 'react-router-dom'

function App() {
  

  return (
   
    <BrowserRouter>
      <Routes>
          <Route path='/login' element={ <TelaLogin />} />
          <Route path='/register' element={ <Cadastro />} />
          
          {/* Rotas protegidas */}
          <Route path='/esqueci_minha_senha' 
            element={ 
              <RotaProtegida>
                <MudarSenha />
              </RotaProtegida>} 
          />
          <Route path='/redefinir_senha' 
          element={ 
              <RotaProtegida>
                <NovaSenha />
              </RotaProtegida>} 
          />
          
          <Route path='/inicio' element={ 
              <RotaProtegida>
                <TelaInicial />
              </RotaProtegida>} 
          />
          
          <Route path='/criar_tarefa' element={ 
              <RotaProtegida>
                <NovaTarefa />
              </RotaProtegida>} 
          />
          
          <Route path='/tarefa' element={ 
              <RotaProtegida>
                <VisualizarTarefa />
              </RotaProtegida>} 
          />
          
          <Route path='/perfil' element={ 
              <RotaProtegida>
                <VisualizarPerfil />
              </RotaProtegida>} 
          />
          
          <Route path="/" element={<Navigate to="/login" />} />
      </Routes>
    </BrowserRouter>

  )
}

export default App
