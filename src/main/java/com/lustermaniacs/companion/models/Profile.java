package com.lustermaniacs.companion.models;

public class Profile {

    // Instance Fields
        String first_name;
        String last_name;
        Byte age;
        char gender;
        String[] profile_pic = new String[5];
        String location;
        int max_distance;
        String bio;
        int[] survey_results = new int[5];
        int[] sysmatched_users = new int[100];

    // Constructor
        public Profile(String first_name, String last_name, Byte age, char gender, String[] profile_pic, String location, int max_distance, String bio, int[] survey_results, int[] sysmatched_users) {
            this.first_name = first_name;
            this.last_name = last_name;
            this.age = age;
            this.gender = gender;
            this.profile_pic = profile_pic;
            this.location = location;
            this.max_distance = max_distance;
            this.bio = bio;
            this.survey_results = survey_results;
            this.sysmatched_users = sysmatched_users;
        }

    //Getter

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public Byte getAge() {
            return age;
        }

        public char getGender() {
            return gender;
        }

        public String[] getProfile_pic() {
            return profile_pic;
        }

        public String getLocation() {
            return location;
        }

        public int getMax_distance() {
            return max_distance;
        }

        public String getBio() {
            return bio;
        }

        public int[] getSurvey_results() {
            return survey_results;
        }

        public int[] getSysmatched_users() {
            return sysmatched_users;
        }

    // Setter

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setProfile_pic(String[] profile_pic) {
        this.profile_pic = profile_pic;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMax_distance(int max_distance) {
        this.max_distance = max_distance;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setSurvey_results(int[] survey_results) {
        this.survey_results = survey_results;
    }

    public void setSysmatched_users(int[] sysmatched_users) {
        this.sysmatched_users = sysmatched_users;
    }
}
