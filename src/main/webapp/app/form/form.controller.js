(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('FormController',   FormController);

    FormController.$inject = ['$scope', 'Form'];

    function FormController ($scope, Form) {
        var vm = this;

        $scope.fields = {
            libelle:["Entreprise", "Responsable", "Enseignant", "Filière"],
            data:[
                {ms:"", mi:"", si:false},
                {ms:"", mi:"", si:false},
                {ms:"", mi:"", si:false},
                {ms:"", mi:"", si:false},
            ]
        };

        $scope.change = function(i){
            $scope.fields.data[i].si = true;
        }

        $scope.display = function(i){
            if(i-1 < 0)
                return true;
            else
                return $scope.fields.data[i-1].si;
        }

        $scope.go = function(){
            var data = {};
            $scope.fields.data.forEach(function(current){
                var tmp = current.ms;
                if(tmp != ""){
                    data[tmp] = current.mi;
                } else {
                    data[tmp] = "*";
                }
            })
            console.log("controller : ");
            console.log(data);
            Form.get({data:[data.Entreprise, data.Responsable, data.Enseignant, data.Filiere]}, function(res){
                res.forEach(function(r){
                    console.log(r)
                });
            });

        }
    }
})();
