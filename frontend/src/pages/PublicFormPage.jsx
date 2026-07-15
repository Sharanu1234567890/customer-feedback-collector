import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import client from '../api/client';
import QuestionInput from '../components/QuestionInput';

export default function PublicFormPage() {
  const { token } = useParams();
  const [form, setForm] = useState(null);
  const [answers, setAnswers] = useState({});
  const [rating, setRating] = useState(null);
  const [submitted, setSubmitted] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    client.get(`/form/${token}`)
      .then(res => setForm(res.data))
      .catch(() => setError('Form not found or link is invalid.'));
  }, [token]);

  const handleAnswerChange = (questionId, value, isRating) => {
    setAnswers(prev => ({ ...prev, [questionId]: value }));
    if (isRating) setRating(Number(value));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!rating) {
      setError('Please provide an overall rating before submitting.');
      return;
    }
    try {
      const payload = {
        token,
        rating,
        answers: Object.entries(answers).map(([questionId, value]) => ({
          questionId: Number(questionId),
          value
        }))
      };
      await client.post('/responses', payload);
      setSubmitted(true);
    } catch {
      setError('Something went wrong submitting your feedback.');
    }
  };

  if (error && !form) return <div className="container"><p className="error">{error}</p></div>;
  if (!form) return <div className="container"><p>Loading...</p></div>;

  if (submitted) {
    return (
      <div className="container">
        <h1>Thank you! 🎉</h1>
        <p>Your feedback has been submitted.</p>
      </div>
    );
  }

  return (
    <div className="container">
      <h1>{form.title}</h1>
      <form onSubmit={handleSubmit}>
        {form.questions.sort((a, b) => a.orderIndex - b.orderIndex).map(q => (
          <div className="field" key={q.id}>
            <label>{q.text}</label>
            <QuestionInput
              question={q}
              value={answers[q.id]}
              onChange={(val) => handleAnswerChange(q.id, val, q.type === 'RATING')}
            />
          </div>
        ))}
        {error && <p className="error">{error}</p>}
        <button type="submit">Submit Feedback</button>
      </form>
    </div>
  );
}