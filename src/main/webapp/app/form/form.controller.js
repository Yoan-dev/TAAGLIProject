(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('FormController', FormController);

    FormController.$inject = ['$scope', 'Etudiant'];

    function FormController ($scope, Etudiant) {
        var vm = this;

        vm.fields = ['Nom', 'Prénom', 'Numéro étudiant'];
        vm.etudiants = Etudiant.query();

        vm.form.etudiant = "";

        
    }
})();
