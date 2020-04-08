Scenario: User should receive list of all Star Wars movies
Given I create a get request to search for FILMS
When I send a request
Then my response returns list A New Hope, Attack of the Clones, The Phantom Menace, Revenge of the Sith, Return of the Jedi, The Empire Strikes Back, The Force Awakens

Scenario: User should receive a successful response
Given I create a get request to search for VEHICLES
When I send a request
Then my response returns code 200

Scenario: User should find a person by name
Given I create a get request to search for PEOPLE
When I search for Captain Phasma
Then my search specification is found in the response