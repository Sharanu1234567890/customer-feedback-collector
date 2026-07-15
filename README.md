---

## 💻 Local Setup

### Prerequisites
- Java 17+
- Node.js 18+
- PostgreSQL (or Docker, for a local instance)

### 1. Database
```bash
docker run --name feedback-db -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=feedback -p 5432:5432 -d postgres
```

### 2. Backend
```bash
cd backend
mvn spring-boot:run
```
Runs on `http://localhost:8080`

Insert a test admin (no signup flow exists):
```sql
INSERT INTO admins (email, password) VALUES ('admin@test.com', 'admin123');
```

### 3. Frontend
```bash
cd frontend
npm install
cp .env.example .env
npm run dev
```
Runs on `http://localhost:5173`

---

## 🤖 AI Tools Used

Built with **Claude** as an AI pair-programmer throughout:

- **Schema & entity design** — JPA entity relationships (Form → Question → Response → Answer) generated and reviewed together
- **Boilerplate generation** — repositories, services, controllers scaffolded via AI, then manually verified against the intended API contract
- **Debugging (real example)** — hit a **bidirectional JPA infinite recursion bug**: `GET /form/{token}` returned deeply nested, endlessly repeating JSON (`Form → Question → Form → Question...`). Diagnosed the cause (Jackson serializing both sides of the `@OneToMany`/`@ManyToOne` relationship), and fixed it by adding `@JsonIgnore` on the child-side references — not by blindly pasting a suggested fix, but after understanding *why* the recursion was happening.
- **Deployment troubleshooting** — resolved a Render-specific Vite permission error (`vite: Permission denied`) by switching the build command to `npm ci && npm run build`, and fixed a separate SPA routing issue (`404` on client-side routes) by adding a `/* → /index.html` rewrite rule.

Every AI-suggested change was tested against the running app before being accepted — this is covered in the demo video.

---

## ⚠️ Known Limitations

Deliberate scope trade-offs made given the project time limit:

- **Auth is a simple email/password check**, not full Spring Security/JWT
- **No per-admin form ownership** — any logged-in admin currently sees all forms
- **No duplicate-submission prevention** on the public form
- **No field-level validation** beyond the overall rating being required

These were conscious cuts to prioritize a fully working, deployed core loop over partial coverage of edge cases.

---

## 📹 Demo Video

[Link to be added]

Covers: problem & approach → live product walkthrough → code walkthrough → AI tool usage (including the recursion bug above).
