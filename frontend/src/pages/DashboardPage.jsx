import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import client from '../api/client';
import StatsCard from '../components/StatsCard';

export default function DashboardPage() {
  const { formId } = useParams();
  const [data, setData] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    client.get(`/dashboard/${formId}`)
      .then(res => setData(res.data))
      .catch(() => setError('Could not load dashboard.'));
  }, [formId]);

  if (error) return <div className="container"><p className="error">{error}</p></div>;
  if (!data) return <div className="container"><p>Loading...</p></div>;

  return (
    <div className="container">
      <Link to="/">&larr; Back to Forms</Link>
      <h1 style={{ marginTop: 12 }}>Dashboard</h1>

      <div className="stats-row">
        <StatsCard label="Total Responses" value={data.totalResponses} />
        <StatsCard label="Average Rating" value={data.averageRating.toFixed(1)} />
      </div>

      <h2>Recent Submissions</h2>
      {data.recent.length === 0 && <p>No responses yet.</p>}
      {data.recent.map(r => (
        <div className="response-item" key={r.id}>
          Rating: <strong>{r.rating}</strong> — {new Date(r.submittedAt).toLocaleString()}
        </div>
      ))}
    </div>
  );
}