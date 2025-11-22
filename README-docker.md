# Docker pack for bank-account-management-system

Files included:
- Dockerfile
- docker-compose.yml
- .dockerignore

## How to use

1. Copy these files to the root of your Spring Boot project (same folder as `pom.xml`).
2. In a terminal, from that folder, run:

   ```bash
   docker compose up --build
   ```

3. The API will be available at: `http://localhost:8080`
4. MongoDB will be available at: `mongodb://localhost:27017/bankingdb`
