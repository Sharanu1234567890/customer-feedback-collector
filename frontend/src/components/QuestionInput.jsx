export default function QuestionInput({ question, value, onChange }) {
  if (question.type === 'RATING') {
    return (
      <div className="rating-group">
        {[1, 2, 3, 4, 5].map(n => (
          <span
            key={n}
            className={value === String(n) ? 'active' : ''}
            onClick={() => onChange(String(n))}
          >
            {n}
          </span>
        ))}
      </div>
    );
  }

  if (question.type === 'MULTIPLE_CHOICE') {
    return (
      <select value={value || ''} onChange={(e) => onChange(e.target.value)}>
        <option value="">Select...</option>
        <option value="Yes">Yes</option>
        <option value="No">No</option>
        <option value="Maybe">Maybe</option>
      </select>
    );
  }

  // default TEXT
  return (
    <textarea
      rows={3}
      value={value || ''}
      onChange={(e) => onChange(e.target.value)}
      placeholder="Your answer..."
    />
  );
}