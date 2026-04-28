# Jackpot Service

Spring Boot backend service that receives bets, publishes them to a Kafka-like topic, consumes them, contributes to jackpot pools, and evaluates jackpot rewards.

## Implemented requirements

- `POST /bets` publishes a bet to `jackpot-bets`.
- Consumer listens to the topic and processes contributions.
- Contribution strategies:
  - fixed percentage
  - variable percentage (decreases as jackpot grows, with floor)
- `POST /bets/{betId}/evaluate` evaluates reward winner.
- Reward strategies:
  - fixed chance
  - variable chance (increases as jackpot grows, hits 100% at configured limit)
- Jackpot resets to initial pool amount when reward is won.
- In-memory H2 DB used for persistence (`jackpots`, `jackpot_contributions`, `jackpot_rewards`).

## Architecture

- **API**: `BetController`
- **Kafka publish**: `MockKafkaBetPublisher` (logs payload and dispatches to consumer for local run)
- **Kafka consumer**: `BetTopicConsumer`
- **Core logic**: `BetProcessingService`
- **Strategies**:
  - Contribution: `FixedContributionStrategy`, `VariableContributionStrategy`
  - Reward: `FixedRewardStrategy`, `VariableRewardStrategy`

## Prerequisites

- Java 26
- No external DB/Kafka required (H2 + mock publisher)

## Run

```bash
./gradlew bootRun
```

Service starts on `http://localhost:8080`.

## Example usage

### 1) Publish bet

```bash
curl -X POST http://localhost:8080/bets \
  -H "Content-Type: application/json" \
  -d '{
    "betId": "bet-1001",
    "userId": "user-42",
    "jackpotId": "JACKPOT-1",
    "betAmount": 120.00
  }'
```

Response:

```json
{
  "status": "PUBLISHED",
  "betId": "bet-1001"
}
```

### 2) Evaluate bet for jackpot reward

```bash
curl -X POST http://localhost:8080/bets/bet-1001/evaluate
```

Response:

```json
{
  "betId": "bet-1001",
  "winner": false,
  "rewardAmount": 0.00,
  "currentJackpotAmount": 1006.00
}
```

## Configuration

Default jackpot setup is in `src/main/resources/application.yml` under `jackpot.defaults.jackpots`.

## Tests

Run all tests:

```bash
./gradlew test
```

Included test coverage:

- Unit:
  - contribution strategy fixed + variable
  - reward strategy fixed + variable
  - jackpot reset after win
- Integration:
  - `POST /bets` triggers flow
  - evaluate endpoint returns expected structure
