(function() {
    'use strict';
    angular
        .module('taagliProjectApp')
        .factory('Etudiant', Etudiant);

    Etudiant.$inject = ['$resource', 'DateUtils'];

    function Etudiant ($resource, DateUtils) {
        var resourceUrl =  'api/etudiants/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateDeNaissance = DateUtils.convertLocalDateFromServer(data.dateDeNaissance);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateDeNaissance = DateUtils.convertLocalDateToServer(data.dateDeNaissance);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateDeNaissance = DateUtils.convertLocalDateToServer(data.dateDeNaissance);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
