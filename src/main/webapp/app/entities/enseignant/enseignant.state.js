(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('enseignant', {
            parent: 'entity',
            url: '/enseignant?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Enseignants'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enseignant/enseignants.html',
                    controller: 'EnseignantController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('enseignant-detail', {
            parent: 'entity',
            url: '/enseignant/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Enseignant'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enseignant/enseignant-detail.html',
                    controller: 'EnseignantDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Enseignant', function($stateParams, Enseignant) {
                    return Enseignant.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'enseignant',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('enseignant-detail.edit', {
            parent: 'enseignant-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enseignant/enseignant-dialog.html',
                    controller: 'EnseignantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Enseignant', function(Enseignant) {
                            return Enseignant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enseignant.new', {
            parent: 'enseignant',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enseignant/enseignant-dialog.html',
                    controller: 'EnseignantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                prenom: null,
                                telephone: null,
                                mail: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('enseignant', null, { reload: 'enseignant' });
                }, function() {
                    $state.go('enseignant');
                });
            }]
        })
        .state('enseignant.edit', {
            parent: 'enseignant',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enseignant/enseignant-dialog.html',
                    controller: 'EnseignantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Enseignant', function(Enseignant) {
                            return Enseignant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enseignant', null, { reload: 'enseignant' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enseignant.delete', {
            parent: 'enseignant',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enseignant/enseignant-delete-dialog.html',
                    controller: 'EnseignantDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Enseignant', function(Enseignant) {
                            return Enseignant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enseignant', null, { reload: 'enseignant' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
