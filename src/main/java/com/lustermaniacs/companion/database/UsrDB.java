package com.lustermaniacs.companion.database;

interface UsrDB{
    int addUser(User user);
    int userUpdate(String username);
    //    void UserUpdateBio(String username, String[] PFP, String Bio);
//    void UserUpdateLocSurvey(String username, String Location, int MaxDist, int[] SurveyResults, int[] Sysmatched);
    int deleteUser(User user);
    User getMatchedUsers(User user);

}
