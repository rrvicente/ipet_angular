'use strict';

angular.module('app').controller('listarAtendimentoItensController',['$scope', '$http', 'MyService',
				function($scope, $http, MyService) {

					$scope.atendimento = MyService.data.atendimento;
					$scope.allItensAtendimento = {};

					$scope.listarItensAtendimento = function() {
						var id_atendimento = '?id=' + $scope.atendimento.id;
						
						$http.get('/ipet_angular/rest/atendimento/listar_itens' + id_atendimento).success(
								function(data) {
									$scope.allItensAtendimento = data;
								}).error(function() {
							alert("Falha em obter dados de atendimentos");
						});
					};
					$scope.listarItensAtendimento();
					
				} ]);
im