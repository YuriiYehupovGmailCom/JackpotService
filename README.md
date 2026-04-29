## Run

```bash
docker-compose up
```

- `JACKPOT-1` has a fixed low win chance: `1%`.
- `JACKPOT-2` starts with pool `120.00`, contributes `100%` of each bet amount, and guarantees a win when the pool reaches `150.00`.

### Scenario 1: Publish bet with low chance to win

Expected result: `winner` will usually be `false`, because `JACKPOT-1` has only a `1%` fixed reward chance.

```bash
curl -s -w '\n' -X POST http://localhost:8080/bets \
  -H "Content-Type: application/json" \
  -d '{
    "betId": "low-chance-1",
    "userId": "user-low",
    "jackpotId": "JACKPOT-1",
    "betAmount": 120.00
  }'
curl -s -w '\n' -X POST http://localhost:8080/bets/low-chance-1/evaluate
```

Expected response with **"winner":false**:

```bash
{"status":"PUBLISHED","betId":"low-chance-1"}
{"betId":"low-chance-1","winner":false,"rewardAmount":0.00}
```

### Scenario 2: Publish 1 bet and win because Guaranteed Limit Reached

`JACKPOT-2` starts at `120.00`. A `30.00` bet contributes `30.00`, so the pool reaches the guaranteed limit: `150.00`.

```bash
curl -s -w '\n' -X POST http://localhost:8080/bets \
  -H "Content-Type: application/json" \
  -d '{
    "betId": "guaranteed-one-bet",
    "userId": "user-guaranteed-1",
    "jackpotId": "JACKPOT-2",
    "betAmount": 30.00
  }'
curl -s -w '\n' -X POST http://localhost:8080/bets/guaranteed-one-bet/evaluate
```

Expected response with **"winner":true**:

```bash
{"status":"PUBLISHED","betId":"guaranteed-one-bet"}
{"betId":"guaranteed-one-bet","winner":true,"rewardAmount":150.00}
```

### Scenario 3: Publish 2 bets and win on the 3rd bet

`JACKPOT-2` starts at `120.00`. Each `10.00` bet contributes `10.00`:

- after bet 1: pool is `130.00`
- after bet 2: pool is `140.00`
- after bet 3: pool is `150.00`, so the win is guaranteed

```bash
curl -s -w '\n' -X POST http://localhost:8080/bets \
  -H "Content-Type: application/json" \
  -d '{
    "betId": "third-bet-1",
    "userId": "user-third-1",
    "jackpotId": "JACKPOT-2",
    "betAmount": 10.00
  }'
curl -s -w '\n' -X POST http://localhost:8080/bets \
  -H "Content-Type: application/json" \
  -d '{
    "betId": "third-bet-2",
    "userId": "user-third-2",
    "jackpotId": "JACKPOT-2",
    "betAmount": 10.00
  }'
curl -s -w '\n' -X POST http://localhost:8080/bets \
  -H "Content-Type: application/json" \
  -d '{
    "betId": "third-bet-3",
    "userId": "user-third-3",
    "jackpotId": "JACKPOT-2",
    "betAmount": 10.00
  }'
curl -s -w '\n' -X POST http://localhost:8080/bets/third-bet-1/evaluate
curl -s -w '\n' -X POST http://localhost:8080/bets/third-bet-2/evaluate
curl -s -w '\n' -X POST http://localhost:8080/bets/third-bet-3/evaluate
```

Expected response with **"third-bet-3","winner":true**:

```bash
{"status":"PUBLISHED","betId":"third-bet-1"}
{"status":"PUBLISHED","betId":"third-bet-2"}
{"status":"PUBLISHED","betId":"third-bet-3"}
{"betId":"third-bet-1","winner":false,"rewardAmount":0.00}
{"betId":"third-bet-2","winner":false,"rewardAmount":0.00}
{"betId":"third-bet-3","winner":true,"rewardAmount":150.00}
```

## Troubleshooting

If any issues with docker

1. pull the code from repository and enter the folder
```bash
git clone https://github.com/YuriiYehupovGmailCom/JackpotService.git
cd JackpotService
```
2. build
```bash
./gradlew build
```
3. run application 
```bash
./gradlew bootRun
```
4. Try Scenarios described above
