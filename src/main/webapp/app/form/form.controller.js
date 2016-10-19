(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('FormController',   FormController);

    FormController.$inject = ['$scope', 'Form', 'Entreprise', 'Responsable', 'Enseignant', 'Filiere'];

    function FormController ($scope, Form, Entreprise, Responsable, Enseignant, Filiere) {
        var vm = this;

        $scope.students = [];
        $scope.champs = ["Selectionner un champ", "Entreprise", "Responsable", "Enseignant", "Filière"];
        $scope.models = [
            {ms:$scope.champs[0], mi:"", data:[]},
            {ms:$scope.champs[0], mi:"", data:[]},
            {ms:$scope.champs[0], mi:"", data:[]},
            {ms:$scope.champs[0], mi:"", data:[]}
        ];

        $scope.display = function(i){
            if(i == 0)
                return true;
            else
                return $scope.models[i-1].ms != $scope.champs[0];
        }

        $scope.change = function(i){
            switch($scope.models[i].ms){
                case 'Entreprise':
                    Entreprise.query({}, function(res){
                        $scope.models[i].data = [];
                        res.forEach(function(current){
                            $scope.models[i].data.push({id:current.id,nom:current.nom});
                        });
                        $scope.models[i].mi = $scope.models[i].data[0].id;
                    });
                    break;
                case 'Responsable':
                    Responsable.query({}, function(res){
                        $scope.models[i].data = [];
                        res.forEach(function(current){
                            $scope.models[i].data.push({id:current.id,nom:current.nom});
                        });
                        $scope.models[i].mi = $scope.models[i].data[0].id;
                    });
                    break;
                case 'Enseignant':
                    Enseignant.query({}, function(res){
                        $scope.models[i].data = [];
                        res.forEach(function(current){
                            $scope.models[i].data.push({id:current.id,nom:current.nom});
                        });
                        $scope.models[i].mi = $scope.models[i].data[0].id;
                    });
                    break;
                case 'Filière':
                    Filiere.query({}, function(res){
                        $scope.models[i].data = [];
                        res.forEach(function(current){
                            $scope.models[i].data.push({id:current.id,nom:current.nom});
                        });
                        $scope.models[i].mi = $scope.models[i].data[0].id;
                    });
                    break;
            }
        }

        $scope.go = function(){
            var data = {Entreprise:"*",Responsable:"*",Enseignant:"*",Filière:"*"};
            $scope.models.forEach(function(current){
                var tmp = current.ms;
                if(tmp != $scope.champs[0]){
                    data[tmp] = current.mi;
                }
            })
            console.log($scope.models);
            console.log(data);
            Form.get({data:[data.Entreprise, data.Responsable, data.Enseignant, data.Filière]}, function(res){
                $scope.students = [];
                res.forEach(function(current){
                    $scope.students.push({nom:current.nom+" "+current.prenom, mail:current.mail});
                });
            });

        }
    }
})();
