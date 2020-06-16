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
            //alert(userArtist.id);
            userArtist.musicId = userArtist.id;
            $http.post('sys/user/playMusic/',userArtist).success(function(result) {
                if(result != "failed") {
                    var mp3 = result;
                    var mp3 = new Audio(mp3);
                    mp3.play();
                }
                else alert("fail!")
            });

      };

       $scope.fetchArtistList = function(userID) {
            $http.get('sys/user/artistlist',userID).success(function(data){
                $scope.artists = data;
            });
       };

       $scope.fetchMusicList();
    });

     $scope.beginMusic = function(Music,musicPath) {
            music.src=musicPath;//改变音乐的SRC
            if(music.readyState="complete"){
                  setTimeout(function(){
                                        duration.innerHTML=parseInt(music.duration/60)+":"+parseInt(music.duration%60);
                                    },1000);//一秒后读取音乐的总时长

                                }
          };