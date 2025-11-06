namespace java com.example.thrift.api

// Example data structures
struct Address {
  1: required string street
  2: required string city
  3: optional string zipCode
}

struct User {
  1: required string id
  2: required string name
  3: optional i32 age
  4: optional Address address
  5: optional list<string> hobbies
  6: optional map<string, string> metadata
}

// Example service which defines API methods
service UserService {
  // Get a user by ID
  User getUserById(1: string id),

  // Create or update a user; return true on success
  bool upsertUser(1: User user),

  // List users with optional paging (very simple example)
  list<User> listUsers(1: i32 offset, 2: i32 limit)
}
