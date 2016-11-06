(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('FormController', FormController);

    FormController.$inject = ['$scope', 'Form', 'Mailing', 'Entreprise', 'Responsable', 'Enseignant', 'Filiere'];

    function FormController ($scope, Form, Mailing, Entreprise, Responsable, Enseignant, Filiere) {
        var vm = this;

        $scope.students = [];
        $scope.champs = ["Sélectionner un champ", "Entreprise", "Responsable", "Enseignant", "Filière"];
        $scope.models = [
            {ms:$scope.champs[0], mi:"", used:false, data:[]},
            {ms:$scope.champs[0], mi:"", used:false, data:[]},
            {ms:$scope.champs[0], mi:"", used:false, data:[]},
            {ms:$scope.champs[0], mi:"", used:false, data:[]}
        ];
        var used = [];

		$scope.login = "";
		$scope.password = "";
		$scope.subject = "";
		$scope.text = "";

        $scope.display = function(i){
            if(i == 0)
                return true;
            else
                return $scope.models[i-1].ms != $scope.champs[0];
        }

        $scope.disabled = function(type){
            return used.indexOf(type) !== -1;
        }

        $scope.change = function(i){
            used = [];
            $scope.models.forEach(function(current){
                var tmp = current.ms;
                if(tmp != $scope.champs[0])
                    used.push(tmp);
            });

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
                            $scope.models[i].data.push({id:current.id,nom:current.nom+" "+current.prenom});
                        });
                        $scope.models[i].mi = $scope.models[i].data[0].id;
                    });
                    break;
                case 'Enseignant':
                    Enseignant.query({}, function(res){
                        $scope.models[i].data = [];
                        res.forEach(function(current){
                            $scope.models[i].data.push({id:current.id,nom:current.nom+" "+current.prenom});
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
            });
            Form.get({data:[data.Entreprise, data.Responsable, data.Enseignant, data.Filière]}, function(res){
                $scope.students = [];
                if(res.length == 0)
                    $scope.students.push({nom:"Aucun résultat", mail:"Aucun résultat"});
                res.forEach(function(current){
                    $scope.students.push({nom:current.nom+" "+current.prenom, mail:current.mail});
                });
            });

        }

        $scope.sendEmail = function(){
        	var mailingList = [$scope.login, $scope.password, $scope.subject, $scope.text];
        	$scope.students.forEach(function(current){
        		mailingList.push(current.mail);
        	});
        	// en réponse à un problème qui tronque la dernière chaine possédant un '.'
        	mailingList.push("end.end");
        	console.log(mailingList);
        	Mailing.post({data:mailingList}, function(res){});

            used = [];
            $scope.students = [];
            $scope.models = [
                {ms:$scope.champs[0], mi:"", used:false, data:[]},
                {ms:$scope.champs[0], mi:"", used:false, data:[]},
                {ms:$scope.champs[0], mi:"", used:false, data:[]},
                {ms:$scope.champs[0], mi:"", used:false, data:[]}
            ];
        }

    }
})();
