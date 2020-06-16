var app = angular.module('music', []);
      app.controller('indexCtrl', function($http,$scope) {
      $scope.userArtist = {};
      $scope.fetchMusicList = function() {
          $http.get('sys/user/musiclist').success(function(data){
              $scope.musics = data;
          });
      };
      $scope.playMusic = function(userArtist,userID) {
            userArtist.userID = userID;
            alert(userArtist.id);
            userArtist.musicId = userArtist.id;
            $http.post('sys/user/playMusic/',userArtist).success(function(result) {
                if("succ" == result) alert("succ!")
                else alert("fail!")
            });
      };
      $scope.fetchMusicList();
    });