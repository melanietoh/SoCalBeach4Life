package com.example.socalbeach4life;

import java.util.ArrayList;

public class DatabaseModeler {

    private ArrayList<UserModel> users;
    private ArrayList<BeachModel> beaches;
    private ArrayList<TripModel> trips;
    private ArrayList<ReviewModel> reviews;
    public DatabaseModeler() {
        users = new ArrayList<>();
        initBeaches();
        trips = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    private void initBeaches() {
        beaches = new ArrayList<BeachModel>();
        ArrayList<ParkingLotModel> parkingLots = new ArrayList<ParkingLotModel>();

        parkingLots.add(new ParkingLotModel("Lot 9", "89 Ashland Ave, Santa Monica, CA 90405", 14.7, 34.0, -118.48));
        parkingLots.add(new ParkingLotModel("Parking", "2100 Ocean Front Walk, Venice, CA 90291", 13.7, 33.98, -118.47));
        ArrayList<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();
        restaurants.add(new RestaurantModel("Tocaya - Venice", "https://tocaya.com/menu/", "11:30am-10pm", 34.08, -117.95, 0.1));
        restaurants.add(new RestaurantModel("High Rooftop Lounge", "https://www.yelp.com/biz/high-rooftop-lounge-venice?osq=high+rooftop+lounge", "3pm-10pm", 33.988, -118.47, 0.1));
        restaurants.add(new RestaurantModel("Venice Way Pizza", "https://www.yelp.com/biz/venice-way-pizza-venice", "12pm-8pm", 33.988, -118.47, 0.1));
        restaurants.add(new RestaurantModel("The Cow's End Cafe", "https://www.yelp.com/biz/the-cows-end-cafe-venice", "6am-6pm", 34.01, -118.5, 0.6));
        restaurants.add(new RestaurantModel("Cafe Gratitude Venice", "https://www.yelp.com/biz/cafe-gratitude-venice", "9am-9pm", 33.99801, -118.47, 0.8));
        beaches.add(new BeachModel("Venice Beach", "1800 Ocean Front Walk\nVenice, CA 90291", "6am - 12am",33.99, -118.47, parkingLots, null, restaurants));

        parkingLots.clear();
        restaurants.clear();
        parkingLots.add(new ParkingLotModel("111 26th St Parking", "111 26th St, Manhattan Beach, CA 90266", 18.5, 33.89, -118.41));
        parkingLots.add(new ParkingLotModel("Dune Park Parking", "Manhattan Beach, CA 90266", 16.8, 33.90, -118.41));
        restaurants.add(new RestaurantModel("Bobo Chinese Deli", "https://www.yelp.com/biz/bobo-chinese-deli-manhattan-beach-2", "11am-9:30pm", 32.791, -116.96, 0.2));
        restaurants.add(new RestaurantModel("North End Caffe", "https://www.google.com/search?client=safari&rls=en&q=north+end+caffe&ie=UTF-8&oe=UTF-8", "8am-3pm", 32.699, -116.95, 0.4));
        restaurants.add(new RestaurantModel("Fishbar Manhattan Beach Seafood Restaurant", "https://www.fishbarmb.com/#fishbarr", "11am-12am", 32.79, -116.96, 1.8));
        restaurants.add(new RestaurantModel("zinc@shade", "https://www.yelp.com/biz/zinc-shade-manhattan-beach", "7am-9pm", 33.88, -118.409, 0.8));
        restaurants.add(new RestaurantModel("Pancho's Restaurant", "https://www.yelp.com/biz/panchos-restaurant-manhattan-beach-6", "11am-9pm", 32.699, -116.95, 0.5));
        beaches.add(new BeachModel("Bruce's Beach", "2600 Highland Ave\nManhattan Beach, CA 90266", "6am - 10PM", 33.89, -118.42, parkingLots, null, restaurants));

        parkingLots.clear();
        restaurants.clear();
        parkingLots.add(new ParkingLotModel("Grand Ave Parking Lot", "12793 Vista Del Mar, Playa Del Rey, CA 90293", 18.0, 33.94, -118.44));
        parkingLots.add(new ParkingLotModel("Dockweiler - Grand Ave", "699 W Grand Ave, El Segundo, CA 90293", 17.7, 33.919, -118.4165));
        restaurants.add(new RestaurantModel("El Segundo Beach Cafe", "https://www.yelp.com/biz/el-segundo-beach-cafe-los-angeles", "9am-4:30pm", 33.92, -118.43, 0.1));
        restaurants.add(new RestaurantModel("Rock & Brews", "https://www.yelp.com/biz/rock-and-brews-el-segundo-el-segundo?osq=Rock+%26+Brews", "11am-9:30pm", 33.918, -118.416, 1.0));
        restaurants.add(new RestaurantModel("Fishbar Manhattan Beach Seafood Restaurant", "https://www.fishbarmb.com/#fishbarr", "11am-12am", 32.79, -116.96, 1.8));
        restaurants.add(new RestaurantModel("Chef Hannes Restaurant", "https://www.yelp.com/biz/chef-hannes-restaurant-el-segundo?osq=Chef+Hannes+Restaurant", "5pm-10pm", 33.92, -118.416, 0.9));
        restaurants.add(new RestaurantModel("Jame Enoteca", "https://www.yelp.com/biz/jame-enoteca-el-segundo-2", "5pm-10pm", 33.915, -118.404, 1.0));
        beaches.add(new BeachModel("Dockweiler Beach", "12000 Vista Del Mar\nPlaya Del Rey, CA 90293", "6am - 10PM", 33.9, -118.4, parkingLots, null, restaurants));

        parkingLots.clear();
        restaurants.clear();
        parkingLots.add(new ParkingLotModel("AirGarage", "200 Culver Blvd, Playa Del Rey, CA 90293", 16.0, 33.959, -118.448));
        parkingLots.add(new ParkingLotModel("422 Campdell St Parking", "422 Campdell St, Playa Del Rey, CA 90293", 17.0, 33.957, -118.44));
        restaurants.add(new RestaurantModel());
        restaurants.add(new RestaurantModel());
        restaurants.add(new RestaurantModel());
        restaurants.add(new RestaurantModel());
        restaurants.add(new RestaurantModel());
        beaches.add(new BeachModel("Playa Del Rey Beach", "Culver Blvd & Pacific Ave\nLos Angeles, CA 90293", "9am - 5PM", 33.95, -118.44, parkingLots, null, restaurants));

        parkingLots.clear();
        restaurants.clear();
        parkingLots.add(new ParkingLotModel("Grand Ave Parking Lot", "12793 Vista Del Mar, Playa Del Rey, CA 90293", 18.0, 33.94, -118.44));
        parkingLots.add(new ParkingLotModel("533 Main St Parking", "533 Main St, El Segundo, CA 90245", 16.0, 33.923, -118.416));
        restaurants.add(new RestaurantModel("The Habit Burger Grill", "https://www.yelp.com/biz/the-habit-burger-grill-el-segundo", "10:30am-11pm", 33.919182, -118.416466, 0.3));
        restaurants.add(new RestaurantModel("Good Stuff Restaurant", "https://www.yelp.com/biz/good-stuff-restaurant-el-segundo-el-segundo", "7am-2pm", 33.915007, -118.4046, 0.2));
        restaurants.add(new RestaurantModel("Ensenada's Surf N Turf Grill", "https://www.yelp.com/biz/ensenadas-surf-n-turf-grill-el-segundo", "10am-8pm", 34.012, -118.413, 0.4));
        restaurants.add(new RestaurantModel("Chef Hannes Restaurant", "https://m.yelp.com/biz/chef-hannes-restaurant-el-segundo	11:30am-2pm", "5-9pm", 33.9682, -118.41202, 0.4));
        restaurants.add(new RestaurantModel("Sausal", "https://www.yelp.com/biz/sausal-el-segundo", "11:30am-8:30pm", 33.98123, -118.4012, 0.7));
        beaches.add(new BeachModel("El Segundo Beach", "Grand Ave & Vista Del Mar Blvd\nEl Segundo, CA 90245", "6am - 10PM", 33.91, -118.42, parkingLots, null, restaurants));
    }

    public boolean testRegisterUser(UserModel u) {
        String s = u.uid;
        users.add(u);
        for (int i = 0; i < users.size(); i++) {
            if (s.equals(users.get(i).getUid())) return true;
        }
        return false;
    }

    public boolean signInValidate(UserModel u) {
        for (int i = 0; i < users.size(); i++) {
            if (u.getUid().equals(users.get(i).getUid())) return true;
        }
        return false;
    }

    //first user for testing purposes
    public UserModel getFirstUser() {
        if (users.size() == 0)
            return null;
        return users.get(0);
    }

    //Copied from return value of DB call
    public ArrayList<BeachModel> getBeaches() {
        return beaches;
    }

    public boolean addTrip(TripModel t) {
        String s = t.tripid;
        trips.add(t);
        for (int i = 0; i < trips.size(); i++) {
            if (s.equals(trips.get(i).tripid)) return true;
        }
        return false;
    }

    public boolean checkTrip(TripModel t) {
        for (int i = 0; i < trips.size(); i++) {
            if (t.tripid.equals(trips.get(i).tripid)) return true;
        }
        return false;
    }

    public boolean addReview(ReviewModel r) {
        String s = r.id;
        for (int i = 0; i < reviews.size(); i++) {
            if (r.beachName.equals(reviews.get(i).beachName)) {
                reviews.remove(i);
                break;
            }
        }
        reviews.add(r);
        for (int i = 0; i < reviews.size(); i++) {
            if (s.equals(reviews.get(i).id)) return true;
        }
        return false;
    }

    public boolean checkReview(ReviewModel r) {
        for (int i = 0; i < reviews.size(); i++) {
            if (r.id.equals(reviews.get(i).id)) return true;
        }
        return false;
    }
}
