(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('ConventionController', ConventionController);

    ConventionController.$inject = ['$scope', 'Adresse', 'Entreprise', 'Responsable', 'Enseignant', 'Filiere', 'Etudiant'];

    function ConventionController ($scope, Adresse, Entreprise, Responsable, Enseignant, Filiere, Etudiant) {
        var vm = this;

        $scope.intitule;
        $scope.date_debut;
        $scope.date_fin;
        $scope.adresse;
        $scope.entreprise;
        $scope.responsable;
        $scope.enseignant;
        $scope.filiere;
        $scope.etudiant;

        $scope.adresses = Adresse.query();
        $scope.entreprises = Entreprise.query();
        $scope.responsables = Responsable.query();
        $scope.enseignants = Enseignant.query();
        $scope.filieres = Filiere.query();
        $scope.etudiants = Etudiant.query();

        $scope.datePickerOpenStatus = {};
        $scope.datePickerOpenStatus.date_debut = false;
        $scope.datePickerOpenStatus.date_fin = false;

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
	}
})();
