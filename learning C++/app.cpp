#include <iostream>
#include "Profile.hpp"

int main() {

    Profile dylan("Dylan F", 21, "Cork", "Ireland");
    dylan.add_hobby("programming");
    dylan.add_hobby("writing");
    dylan.add_hobby("science fiction");
    std::cout << dylan.view_profile();

}