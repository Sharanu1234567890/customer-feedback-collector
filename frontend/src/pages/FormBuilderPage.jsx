import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import client from '../api/client';
import { useAuth } from '../context/AuthContext';

const emptyQuestion = () => ({ text: '', type: 'TEXT', orderIndex: 1 });

export default function FormBuilderPage() {
  const { token, logout } = useAuth();
  const navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [questions, setQuestions] = useState([emptyQuestion()]);
  const [forms, setForms] = useState([]);
  const [error, setError] = useState('');
  const [createdLink, setCreatedLink] = useState('');

  useEffect(() => {
    if (!token) { navigate('/login'); return; }
    loadForms();
  }, [token]);

  const loadForms = () => {
    client.get('/forms').then(res => setForms(res.data)).catch(() => {});
  };

  const updateQuestion = (i, field, value) => {
    const updated = [...questions];
    updated[i][field] = value;
    setQuestions(updated);
  };

  const addQuestion = () => {
    setQuestions([...questions, { text: '', type: 'TEXT', orderIndex: questions.length + 1 }]);
  };

  const removeQuestion = (i) => {
    setQuestions(questions.filter((_, idx) => idx !== i));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    if (!title.trim() || questions.some(q => !q.text.trim())) {
      setError('Title and all question texts are required.');
      return;
    }
    try {
      const res = await client.post('/forms', { title, questions });
      setCreatedLink(`${window.location.origin}/f/${res.data.token}`);
      setTitle('');
      setQuestions([emptyQuestion()]);
      loadForms();
    } catch {
      setError('Failed to create form.');
    }
  };

  const deleteForm = async (id) => {
    await client.delete(`/forms/${id}`);
    loadForms();
  };

  return (
    <div className="container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1>Form Builder</h1>
        <button className="secondary" onClick={() => { logout(); navigate('/login'); }}>Logout</button>
      </div>

      <form onSubmit={handleSubmit}>
        <div className="field">
          <label>Form Title</label>
          <input value={title} onChange={(e) => setTitle(e.target.value)} placeholder="e.g. Cafe Feedback" />
        </div>

        {questions.map((q, i) => (
          <div className="question-block" key={i}>
            <div className="field">
              <label>Question {i + 1}</label>
              <input
                value={q.text}
                onChange={(e) => updateQuestion(i, 'text', e.target.value)}
                placeholder="Question text"
              />
            </div>
            <div className="field">
              <label>Type</label>
              <select value={q.type} onChange={(e) => updateQuestion(i, 'type', e.target.value)}>
                <option value="TEXT">Text</option>
                <option value="RATING">Rating (1-5)</option>
                <option value="MULTIPLE_CHOICE">Multiple Choice</option>
              </select>
            </div>
            {questions.length > 1 && (
              <button type="button" className="danger" onClick={() => removeQuestion(i)}>Remove</button>
            )}
          </div>
        ))}

        <button type="button" className="secondary" onClick={addQuestion} style={{ marginBottom: 12 }}>
          + Add Question
        </button>
        <br />
        {error && <p className="error">{error}</p>}
        <button type="submit">Create Form</button>
      </form>

      {createdLink && (
        <p style={{ marginTop: 16 }}>
          Shareable link: <a href={createdLink} target="_blank" rel="noreferrer">{createdLink}</a>
        </p>
      )}

      <h2 style={{ marginTop: 32 }}>Your Forms</h2>
      {forms.map(f => (
        <div className="response-item" key={f.id}>
          <strong>{f.title}</strong>
          <div>
            <a href={`/dashboard/${f.id}`}>View Dashboard</a> ·{' '}
            <a href={`/f/${f.token}`} target="_blank" rel="noreferrer">Public Link</a> ·{' '}
            <button className="danger" onClick={() => deleteForm(f.id)}>Delete</button>
          </div>
        </div>
      ))}
    </div>
  );
}