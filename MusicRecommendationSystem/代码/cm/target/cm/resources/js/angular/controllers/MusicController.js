'use strict';

/**
 * CarController
 * @constructor
 */
var MusicController = function($scope, $http) {
    $scope.playMusic = function(userID,ArtistID) {
        $http.post('sys/user/playMusic/',data: {  
        	userID: userID,  ArtistID:ArtistID
        }).success(function() {
            $scope.fetchCarsList();
        });
        $scope.carName = '';
    };
};