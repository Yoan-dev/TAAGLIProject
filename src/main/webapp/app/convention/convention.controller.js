(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('ConventionController', ConventionController);

    ConventionController.$inject = ['$window', '$scope', 'Stage', 'Adresse', 'Entreprise', 'Responsable', 'Enseignant', 'Filiere', 'Etudiant'];

    function ConventionController ($window, $scope, Stage, Adresse, Entreprise, Responsable, Enseignant, Filiere, Etudiant) {
        var vm = this;

        $scope.convention;
        $scope.success = false;
        $scope.error = false;

        $scope.adresses = Adresse.query();
        $scope.entreprises = Entreprise.query();
        $scope.responsables = Responsable.query();
        $scope.enseignants = Enseignant.query();
        $scope.filieres = Filiere.query();
        $scope.etudiants = Etudiant.query();

        $scope.datePickerOpenStatus = {};
        $scope.datePickerOpenStatus.dateDebut = false;
        $scope.datePickerOpenStatus.dateFin = false;

        $scope.openCalendar = function(date){
            $scope.datePickerOpenStatus[date] = true;
        }

        $scope.$on('reloadAdresses', function(){
            $scope.adresses = Adresse.query();
        });
        $scope.$on('reloadEntreprises', function(){
            $scope.entreprises = Entreprise.query();
        });
        $scope.$on('reloadResponsables', function(){
            $scope.responsables = Responsable.query();
        });
        $scope.$on('reloadEnseignants', function(){
            $scope.enseignants = Enseignant.query();
        });
        $scope.$on('reloadFilieres', function(){
            $scope.filieres = Filiere.query();
        });
        $scope.$on('reloadEtudiants', function(){
            $scope.etudiants = Etudiant.query();
        });

        $scope.createConvention = function(){
            Stage.save($scope.convention, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            clean();
            $scope.success = true;
            $window.scrollTo(0, 0);
        }

        function onSaveError () {
            $scope.error = true;
            $window.scrollTo(0, 0);
        }

        function clean(){
            $scope.convention = null;
        }
	}
})();
