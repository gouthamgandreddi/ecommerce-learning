# order-service-topic03 — Bug Hunt

This build contains **three deliberate bugs**, planted in exactly what we covered this session:

- **One loud bug** — fails at startup or build, with a clear error message.
- **One silent runtime bug** — runs fine, but lets something through it shouldn't.
- **One semantic mismatch** — technically valid Spring config, but contradicts a principle we established earlier (specifically from Topic 02.6).

No hints beyond that. Find them by running the app and reading the output carefully — the same way you found the N+1 bug in `order-service-v2`.

## Step 1 — try to start it

```bash
mvn spring-boot:run
```

If it doesn't start, paste the **full** error here. Don't summarize it — paste the actual stack trace. Find the bug before you fix it.

## Step 2 — once it's running, go through this sequence in Swagger UI

http://localhost:8080/swagger-ui.html

1. **POST /api/v1/orders** with a valid body. Confirm `201`.
2. **POST /api/v1/orders** with `"purchaseTotal": -5`. Confirm `400` with a field error.
3. **GET /api/v1/orders/{orderId}** on the order you just created. Confirm `200`.
4. **PATCH /api/v1/orders/{orderId}/shipping-status** — but this time, send an **empty body**: `{}`. Watch the response code closely. Then check the H2 console (`http://localhost:8080/h2-console`, JDBC URL `jdbc:h2:mem:orderdb`) and look at that row's `latest_status` column.
5. Look at the **Hibernate SQL log** in your console output right when the app starts up, before you've sent any requests at all. Read it line by line — does anything get auto-generated against the `orders` table that you weren't expecting, given what Flyway is supposed to own?

## Step 3 — report back

Tell me, for each of the three:
- What you observed (the actual log/response, not a paraphrase)
- Where in the code you think it lives
- What you think the fix is

We'll go through each one together — don't fix anything yet.
