import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import PublicFormPage from './pages/PublicFormPage';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import FormBuilderPage from './pages/FormBuilderPage';

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<FormBuilderPage />} />
          <Route path="/f/:token" element={<PublicFormPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/dashboard/:formId" element={<DashboardPage />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;