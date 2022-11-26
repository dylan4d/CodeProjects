#include "Profile.hpp"
#include <iostream>
#include <vector>

Profile::Profile(std::string new_name, int new_age, std::string new_city, std::string new_country) : name(new_name), age(new_age), city(new_city), country(new_country) {

}

std::string Profile::view_profile() {
    std::string profile_bio = "Name: " + name;
    profile_bio += "\nAge: " + std::to_string(age);
    profile_bio += "\nCity: " + city;
    profile_bio += "\nCountry: " + country;
    std::string profile_hobbies = "Hobbies:\n";

    for (std::string hobby : hobbies) {

        profile_hobbies += " - " + hobby + "\n";
    }

    return profile_bio + "\n" + profile_hobbies;
}

void Profile::add_hobby(std::string new_hobby) {
    hobbies.push_back(new_hobby);
}