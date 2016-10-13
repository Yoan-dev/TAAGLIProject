(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('FormController',   FormController);

    FormController.$inject = ['$scope', 'Form'];

    function FormController ($scope, Form) {
        var vm = this;

        $scope.fields = {
            libelle:["Adresse", "Entreprise", "Responsable", "Enseignant", "Filière"],
            data:[
                {ms:"", mi:"", si:false},
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
            Form.get({}, function(res){
                console.log(res);
            });

        }
    }
})();
