import React, { createContext, useState, useEffect, useContext } from 'react';
import api from '../services/api'; 

// 1. Cria o Contexto
const AuthContext = createContext(null);

// 2. Cria o Provedor do Contexto
export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [isLoading, setIsLoading] = useState(true); // Começa carregando
    const [userEmail, setUserEmail] = useState(null);

    useEffect(() => {
        // Função que verifica o status do login ao carregar o app
        const checkAuthStatus = async () => {
            try {
                // Pergunta ao backend se o usuário está logado
                const response = await api.get('/auth/status', { withCredentials: true })

                setUserEmail(response.data.email);
                setIsAuthenticated(true);
                 
            } catch (error) {
                setIsAuthenticated(false); // Se deu erro (401, etc), não está logado
            } finally {
                setIsLoading(false); // Terminou de carregar
            }
        };

        checkAuthStatus();
    }, []); // O array vazio [] garante que isso só rode UMA VEZ

    return (
        <AuthContext.Provider value={{ isAuthenticated, isLoading, userEmail }}>
            {children}
        </AuthContext.Provider>
    );
};

// 3. Hook customizado para usar o contexto mais facilmente
export const useAuth = () => {
    return useContext(AuthContext);
};