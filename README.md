# booking-concerts-system
Building a concert booking system with queues

# ERD 설계
![콘서트_예약서비스_ERD](https://github.com/minseop-94/booking-concerts-system/assets/83794701/e1f64ae3-1dfa-42dd-ac02-a9892441d5ea)

# 시퀀스 다이어그램
## 대기열 토큰 발급 
![대기열 토큰 발급](https://github.com/minseop-94/booking-concerts-system/assets/83794701/801f13f4-7eb1-40df-bf0a-a313dc6fb066)

## 잔액 조회 및 포인트 충전 결제 API
![잔액 조회 및 포인트 충전 결제 API](https://github.com/minseop-94/booking-concerts-system/assets/83794701/79cbe189-d5d2-4629-8097-07a9bcf2210c)

## 좌석 예약 API
![좌석 예약 API](https://github.com/minseop-94/booking-concerts-system/assets/83794701/981ec93d-39bb-4d63-abfc-32d619052456)

## 콘서트 예약 가능한 날짜 및 좌석 조회
![콘서트 예약 가능한 날짜 및 좌석 조회](https://github.com/minseop-94/booking-concerts-system/assets/83794701/58c59b31-2029-4e71-aadc-6325d072690f)


# API 명세 

### 1️⃣ 유저 대기열 토큰 발급 API

**API 기본 정보**

* 메서드: POST
* 인증: 없음
* 요청 URL: `/token?userId`
* 설명: 유저의 UUID를 받아 대기열 토큰을 발급합니다.

**요청 변수**

| 요청 변수명   | 위치    | 타입     | 필수 여부 | 설명       |
| :------- | :---- | :----- | :---- | :------- |
| `userId` | param | string | O     | 유저의 UUID |

**출력(Response)**

| 필드      | 타입     | 설명              |
| :------ | :----- | :-------------- |
| `token` | string | 발급된 토큰 (JWT 형식) |

### 2️⃣ 예약 가능 날짜 조회 API

**API 기본 정보**

* 메서드: GET
* 인증: 필요 (JWT Token)
* 요청 URL: `/concerts/{concertId}/available-dates`
* 설명: 특정 콘서트의 예약 가능한 날짜의 콘서트 목록을 조회합니다.

**요청 변수**

| 요청 변수명      | 위치   | 타입     | 필수 여부 | 설명     |
| :---------- | :--- | :----- | :---- | :----- |
| `concertId` | path | string | O     | 콘서트 ID |

**출력(Response)**

| 필드                   | 타입    | 설명                            |
| :------------------- | :---- | :---------------------------- |
| `ConcertsOptionList` | array | 예약 가능한 콘서트 목록 (ConcertOption) |

### 3️⃣ 예약 가능 좌석 조회 API

**API 기본 정보**

* 메서드: GET
* 인증: 필요 (JWT Token)
* 요청 URL: `/concerts/{concertId}/available-seats`
* 설명: 특정 콘서트의 예약 가능한 좌석 목록을 조회합니다.

**요청 변수**

| 요청 변수명      | 위치   | 타입     | 필수 여부 | 설명        |
| :---------- | :--- | :----- | :---- | :-------- |
| `concertId` | path | string | O     | 콘서트 옵션 ID |

**출력(Response)**

| 필드               | 타입    | 설명                     |
| :--------------- | :---- | :--------------------- |
| `availableSeats` | array | 예약 가능한 좌석 번호 목록 (Seat) |

### 4️⃣ 좌석 예약 요청 API

**API 기본 정보**

* 메서드: PUT
* 인증: 필요 (JWT Token)
* 요청 URL: `/reservations/concerts/{concertId}/seats/{seatId}`
* 설명: 특정 콘서트의 특정 좌석을 예약합니다.

**요청 변수**

| 요청 변수명      | 위치    | 타입     | 필수 여부 | 설명            |
| :---------- | :---- | :----- | :---- | :------------ |
| `concertId` | path  | string | O     | 콘서트 option id |
| `seatId`    | path  | string | O     | 좌석 id         |

**출력(Response)**

| 필드                | 타입    | 설명                                 |
| :------------------ | :------ | :----------------------------------- |
| `reservationId`     | string  | 예약 ID                             |
| `reservationStatus` | string  | 예약 상태 ("pending")              |
| `expirationTime`   | datetime | 임시 예약 만료 시간                 |

### 5️⃣ 잔액 충전 API

**API 기본 정보**

* 메서드: POST
* 인증: 필요 (JWT Token)
* 요청 URL: `/payments/{userId}?amount`
* 설명: 사용자의 잔액을 충전합니다.

**요청 변수**

| 요청 변수명   | 위치    | 타입   | 필수 여부 | 설명     |
| :------- | :---- | :--- | :---- | :----- |
| `amount` | param | int  | O     | 충전할 금액 |
| `userId` | path  | path | O     | 사용자 id |

**출력(Response)**

| 필드        | 타입  | 설명      |
| :-------- | :-- | :------ |
| `balance` | int | 충전 후 잔액 |

### 6️⃣ 잔액 조회 API

**API 기본 정보**

* 메서드: GET
* 인증: 필요 (JWT Token)
* 요청 URL: `/payments/{userId}/balance`
* 설명: 사용자의 현재 잔액을 조회합니다.

**요청 변수**

| 요청 변수명   | 위치   | 타입     | 필수 여부 | 설명     |
| :------- | :--- | :----- | :---- | :----- |
| `userId` | path | string | O     | 사용자 id |

**출력(Response)**

| 필드        | 타입  | 설명    |
| :-------- | :-- | :---- |
| `balance` | int | 현재 잔액 |

### 7️⃣ 결제 API

**API 기본 정보**

* 메서드: POST
* 인증: 필요 (JWT Token)
* 요청 URL: `/payments/{reservationId}`
* 설명: 예약을 확정하고 결제를 처리합니다.

**요청 변수**

| 요청 변수명          | 위치   | 타입     | 필수 여부 | 설명     |
| :-------------- | :--- | :----- | :---- | :----- |
| `reservationId` | path | string | O     | 예약 id  |

**출력(Response)**

| 필드                  | 타입     | 설명                                 |
| :------------------ | :----- | :--------------------------------- |
| `paymentStatus`     | string | 결제 상태 ("success" 또는 "failure")     |
| `reservationStatus` | string | 예약 상태 ("confirmed" 또는 "cancelled") |
| `balance`           | int    | 결제 후 잔액                            |

## API Error 정리

| HTTP 상태 코드                | 에러 코드                  | 에러 메시지             |
| :------------------------ | :--------------------- | :----------------- |
| 400 Bad Request           | `InvalidUserId`        | 유효하지 않은 사용자 ID입니다. |
|                           | `InvalidConcertId`     | 유효하지 않은 콘서트 ID입니다. |
|                           | `InvalidSeatId`        | 유효하지 않은 좌석 ID입니다.  |
|                           | `InvalidAmount`        | 유효하지 않은 금액입니다.     |
|                           | `InvalidReservationId` | 유효하지 않은 예약 ID입니다.  |
|                           | `InsufficientBalance`  | 잔액이 부족합니다.         |
|                           | `SeatAlreadyReserved`  | 이미 예약된 좌석입니다.      |
| 401 Unauthorized          | `InvalidToken`         | 유효하지 않은 토큰입니다.     |
| 404 Not Found             | `ConcertNotFound`      | 콘서트를 찾을 수 없습니다.    |
|                           | `SeatNotFound`         | 좌석을 찾을 수 없습니다.     |
|                           | `ReservationNotFound`  | 예약 정보를 찾을 수 없습니다.  |
| 500 Internal Server Error | `ServerError`          | 서버 오류가 발생했습니다.     |
### 커스텀 에러 정의

* **400 Bad Request:**
    * `InvalidUserId`: 유효하지 않은 사용자 ID입니다.
    * `InvalidConcertId`: 유효하지 않은 콘서트 ID입니다.
    * `InvalidSeatId`: 유효하지 않은 좌석 ID입니다.
    * `InvalidAmount`: 유효하지 않은 금액입니다.
    * `InvalidReservationId`: 유효하지 않은 예약 ID입니다.
    * `InsufficientBalance` : 잔액이 충분하지 않습니다. 
* **401 Unauthorized:**
    * `InvalidToken`: 유효하지 않은 토큰입니다.
* **404 Not Found:**
    * `ConcertNotFound`: 콘서트를 찾을 수 없습니다.
    * `SeatNotFound`: 좌석을 찾을 수 없습니다.
    * `ReservationNotFound`: 예약 정보를 찾을 수 없습니다.
* **409 Conflict:**
    * `SeatAlreadyReserved`: 이미 예약된 좌석입니다.
* **500 Internal Server Error:**
    * `ServerError`: 서버 오류가 발생했습니다.
