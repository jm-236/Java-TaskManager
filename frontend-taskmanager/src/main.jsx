import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import TelaLogin from './pages/login/login.jsx'
import Cadastro from './pages/cadastro/cadastro.jsx'
import MudarSenha from './pages/mudar_senha/mudar_senha.jsx'
import NovaSenha from './pages/nova_senha/nova_senha.jsx'
import TelaInicial from './pages/tela_inicial/tela_inicial.jsx'
import NovaTarefa from './pages/nova_tarefa/nova_tarefa.jsx'
import VisualizarTarefa from './pages/visualizar_tarefa/visualizar_tarefa.jsx'
import VisualizarPerfil from './pages/visualizar_perfil/visualizar_perfil.jsx'
import { PiTelegramLogoThin } from 'react-icons/pi'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    < Cadastro  />
  </StrictMode>,
)
