როგორ გავტესტოთ აპლიკაცია ხელით

ეს ფაილი ხსნის თუ როგორ შეამოწმოთ თქვენი აპლიკაციის API-ის ენდფოინთები Postman-ის ან cURL-ის გამოყენებით.

1. ავტორიზაციის ენდფოინთები
1.1 ახალი მომხმარებლის რეგისტრაცია
ენდფოინთი:
POST /api/auth/register
მოთხოვნის ტანი (JSON):

json
{
  "email": "test@example.com",
  "passwordHash": "password123"
}

cURL ბრძანება:
curl -X POST "http://localhost:8080/api/auth/register" \
     -H "Content-Type: application/json" \
     -d '{"email":"test@example.com", "passwordHash":"password123"}'

1.2 შესვლა და JWT ტოკენის მიღება
ენდფოინთი:
POST /api/auth/login
მოთხოვნის პარამეტრები:
email: test@example.com
password: password123
cURL ბრძანება:
curl -X POST "http://localhost:8080/api/auth/login?email=test@example.com&password=password123"
შენიშვნა: ეს დააბრუნებს JWT ტოკენს, რომელიც საჭიროა სხვა ენდფოინთების ტესტირების დროს ავტორიზაციისთვის.

2. არტისტის ენდფოინთები
2.1 არტისტის შექმნა
ენდფოინთი:
POST /api/artists
მოთხოვნის ტანი (JSON):
{
  "user": { "id": 1 },
  "stageName": "Test Artist",
  "genre": "Rock"
}
cURL ბრძანება:
curl -X POST "http://localhost:8080/api/artists" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     -d '{"user": {"id": 1}, "stageName": "Test Artist", "genre": "Rock"}'

2.2 არტისტის წამოღება ID-ის მიხედვით
ენდფოინთი:
GET /api/artists/{artistId}
cURL ბრძანება:
curl -X GET "http://localhost:8080/api/artists/1" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"

3. ალბომის ენდფოინთები
3.1 ალბომის შექმნა
ენდფოინთი:
POST /api/albums/create-album
მოთხოვნის ტანი (JSON):
{
  "artist": { "id": 1 },
  "title": "Test Album",
  "releaseDate": "2025-03-04T00:00:00"
}
cURL ბრძანება:
curl -X POST "http://localhost:8080/api/albums" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     -d '{"artist": {"id": 1}, "title": "Test Album", "releaseDate": "2025-03-04T00:00:00"}'

3.2 ალბომის წამოღება ID-ის მიხედვით
ენდფოინთი:
GET /api/albums/{albumId}
cURL ბრძანება:
curl -X GET "http://localhost:8080/api/albums/1" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"

4. ფლეილისტის ენდფოინთები
4.1 ფლეილისტის შექმნა
ენდფოინთი:
POST /api/playlists/create-playlist
მოთხოვნის ტანი (JSON):
{
  "title": "My Favorite Songs",
  "user": { "id": 1 }
}
cURL ბრძანება:
curl -X POST "http://localhost:8080/api/playlists" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     -d '{"title": "My Favorite Songs", "user": {"id": 1}}'

4.2 მომხმარებლის ფლეილისტების წამოღება
ენდფოინთი:
GET /api/playlists/user/{userId}
cURL ბრძანება:
curl -X GET "http://localhost:8080/api/playlists/user/1" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"

5. Playlist Track ენდფოინდები
5.1 ტრეკის დამატება ფლეილისტში
ენდფოინთი:
POST /api/playlist-tracks/{playlistId}/tracks/{trackId}
აღწერა: მატებს ტრეკს ფლეილისტში.

მოთხოვნის პარამეტრები:

playlistId: ფლეილისტის ID.
trackId: ტრეკის ID.
cURL ბრძანება:
curl -X POST "http://localhost:8080/api/playlist-tracks/1/tracks/1" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"

6. Recommendation ენდფოინთები
6.1 რეკომენდაციები მომხმარებლისთვის
ენდფოინთი:

swift
GET /api/recommendations/user/{userId}
აღწერა: მომხმარებლისთვის ფლეილისტების რეკომენდაციების მიღება.

მოთხოვნის პარამეტრები:

userId: მომხმარებლის ID.
cURL ბრძანება:
curl -X GET "http://localhost:8080/api/recommendations/user/1" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"

7. Track ენდფოინთები
7.1 ახალი ტრეკის შექმნა
ენდფოინთი:
POST /api/tracks
აღწერა: ახალი ტრეკის შექმნა.

მოთხოვნის ტანი (JSON):

json
{
  "title": "Test Track",
  "artist": "Test Artist",
  "genre": "Rock",
  "duration": "03:30"
}
cURL ბრძანება:


curl -X POST "http://localhost:8080/api/tracks" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     -d '{"title": "Test Track", "artist": "Test Artist", "genre": "Rock", "duration": "03:30"}'
7.2 ტრეკის მიღება ID-ის მიხედვით
ენდფოინთი:

GET /api/tracks/{trackId}
აღწერა: ტრეკის მიღება მისი ID-ის მიხედვით.

მოთხოვნის პარამეტრები:

trackId: ტრეკის ID.
cURL ბრძანება:
curl -X GET "http://localhost:8080/api/tracks/1" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"


8. User ენდფოინთები
8.1 ახალი მომხმარებლის რეგისტრაცია
ენდფოინთი:

arduino
POST /api/users/register
აღწერა: ახალი მომხმარებლის რეგისტრაცია.

მოთხოვნის ტანი (JSON):

json
{
  "email": "test@example.com",
  "passwordHash": "password123",
  "firstName": "Test",
  "lastName": "User"
}
cURL ბრძანება:
curl -X POST "http://localhost:8080/api/users/register" \
     -H "Content-Type: application/json" \
     -d '{"email": "test@example.com", "passwordHash": "password123", "firstName": "Test", "lastName": "User"}'
8.2 მომხმარებლის მიღება ID-ის მიხედვით
ენდფოინთი:
GET /api/users/{userId}
აღწერა: მომხმარებლის დეტალების მიღება მომხმარებლის ID-ის მიხედვით.

მოთხოვნის პარამეტრები:

userId: მომხმარებლის ID.
cURL ბრძანება:

curl -X GET "http://localhost:8080/api/users/1" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"

ერორ კოდები:
403 Forbidden: დარწმუნდით, რომ მიაწვდით ვალიდურ JWT ტოკენს ავტორიზაციის ჰედერში.
401 Unauthorized: შეამოწმეთ, რომ თქვენი შესვლის მონაცემები სწორია.
400 Bad Request: დარწმუნდით, რომ JSON მოთხოვნის სხეული სწორი სტრუქტურისაა.
404 Not Found: დარწმუნდით, რომ მოთხოვნილი რესურსი არსებობს მონაცემთა ბაზაში.
500 Internal Server Error: შეამოწმეთ სერვერის ლოგები პრობლემების მოსაძებნად.