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

function App() {
  

  return (
   
    <BrowserRouter>
      <Routes>
          <Route path='/register' element={ <Cadastro />} />
          <Route path='/esqueci_minha_senha' element={ <MudarSenha />} />
          <Route path='/redefinir_senha' element={ <NovaSenha />} />
          <Route path='/login' element={ <TelaLogin />} />
          <Route path='/login' element={ <TelaLogin />} />
          <Route path='/inicio' element={ <TelaInicial />} />
          <Route path='/criar_tarefa' element={ <NovaTarefa />} />
          <Route path='/tarefa' element={ <VisualizarTarefa />} />
          <Route path='/perfil' element={ <VisualizarPerfil />} />
      </Routes>
    </BrowserRouter>

  )
}

export default App
