namespace java pers.zkx.algo
struct Address {
    1: required string street,
    2: required string city,
    3: required string zipCode
}

// Define User structure with nested Address
struct User {
    1: required string name,
    2: required i32 age,
    3: required Address address,
    4: required list<string> hobbies
}
