# Customer Feedback Collector

Full-stack web app for collecting and analyzing customer feedback. Businesses create customizable feedback forms, share a public link with customers, and track responses through a live analytics dashboard.

**Live App:** https://customer-feedback-collector-f.onrender.com          
**API:** https://feedback-backend-dgw7.onrender.com
**Demo Credentials:** `admin@test.com` / `admin123`

> Hosted on Render's free tier — backend may take 30–60s to respond after inactivity.
    
## Features
   
- Custom form builder (Text, Rating, Multiple Choice questions)
- Unique shareable public link per form, no login required to submit
- Analytics dashboard — total responses, average rating, recent submissions
- Admin authentication
- Full CRUD on forms

## Tech Stack

**Backend:** Java 17 · Spring Boot 3.3 · Spring Data JPA · PostgreSQL · Maven
**Frontend:** React 18 · Vite · React Router · Axios
**Infra:** Render (Docker web service + PostgreSQL + static site)

## Architecture

```
Customer -> Public Form (React) -> POST /responses
                                        |
                          Spring Boot REST API
              --------------------------------------------------------
              | Public: GET /form/{token}, POST /responses            |
              | Admin:  POST /login, /forms (CRUD), GET /dashboard/{id}|
              --------------------------------------------------------
                              Controller -> Service -> Repository
                                        |
                                   PostgreSQL
```

## Project Structure

```
customer-feedback-collector/
├── backend/
│   └── src/main/java/com/feedback/
│       ├── controller/   REST endpoints
│       ├── service/      Business logic
│       ├── repository/   Spring Data JPA
│       ├── model/        JPA entities
│       ├── dto/          Request/response payloads
│       ├── exception/    Global error handling
│       └── config/       CORS
├── frontend/
│   └── src/
│       ├── pages/        PublicFormPage, LoginPage, DashboardPage, FormBuilderPage
│       ├── components/   QuestionInput, StatsCard
│       ├── context/      AuthContext
│       └── api/          Axios client
└── README.md
```

## Local Setup

**Prerequisites:** Java 17+, Node.js 18+, PostgreSQL (or Docker)

```bash
# 1. Database
docker run --name feedback-db -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=feedback -p 5432:5432 -d postgres

# 2. Backend (http://localhost:8080)
cd backend
mvn spring-boot:run

# 3. Frontend (http://localhost:5173)
cd frontend
npm install
cp .env.example .env
npm run dev
```

Seed an admin user (no signup flow):

```sql
INSERT INTO admins (email, password) VALUES ('admin@test.com', 'admin123');
```

## AI Tools Used

Built with Claude as an AI pair-programmer:

- **Design:** JPA entity relationships (Form → Question → Response → Answer) and REST API contract
- **Scaffolding:** repositories, services, controllers, generated then reviewed against the intended contract
- **Debugging:** diagnosed and fixed a bidirectional JPA infinite-recursion bug (Form → Question → Form → ... in JSON responses) by adding `@JsonIgnore` on child-side relationship fields, after identifying the root cause in Jackson's serialization of the `@OneToMany`/`@ManyToOne` pair
- **Deployment:** resolved a Render-specific Vite build permission error via `npm ci && npm run build`, and fixed SPA client-side routing with a `/* → /index.html` rewrite rule

## Known Limitations

- Auth is a basic email/password check, not Spring Security/JWT
- No per-admin form ownership scoping
- No duplicate-submission prevention
- Minimal field-level validation

Scoped intentionally to prioritize a fully working, deployed core loop within the project time limit.

## Demo Video

[link here]
