// JavaScript Document

var Cards = (function()
{
	var filenamePath = "data/data.xml";
	var lastCard;
	var firstCard = 0;
	
	// Get Id card
	var getId = function(id){
		return id.substr(id.length - 1);
	};
	
	// If resize the windows add to widh to card select to make the design responsive
	var resize = function()
	{
		$( window ).resize(function() {
			$('[data-role="card"].selected').css("width","auto");
		});	
	}
	
	// Next button click
	var nextCard = function()
	{
		$('[data-role="nextButton"]').on("click",function(e){
			e.preventDefault();
			
			// get id of the card
			var getIndex = getId($('[data-role="card"].selected').attr('id'));
			
			$('[data-role="card"].selected').css("width",$('[data-role="card"].selected').width());
			$('[data-role="card"].selected').css("left",-$('[data-role="content"]').width());
			$('[data-role="card"].selected').removeClass('selected');
			
			getIndex++;
						
			$('#card_'+getIndex).addClass('selected');

			if (lastCard <= getIndex)
				$('[data-role="nextButton"]').hide();
				
			if ($('[data-role="backButton"]').is(':hidden'))
				$('[data-role="backButton"]').show();
		});
	};
	
	// Back button click
	var backCard = function()
	{
		$('[data-role="backButton"]').on("click",function(e){
			e.preventDefault();
			
			// get id of the card
			var getIndex = getId($('[data-role="card"].selected').attr('id'));
			
			getIndex--;
						
			$('#card_'+getIndex).css("width",$('[data-role="card"].selected').width());
			$('[data-role="card"].selected').removeClass('selected');
			$('#card_'+getIndex).addClass('selected');
			$('[data-role="card"].selected').css("left",0);
			
			if (firstCard == getIndex)
				$('[data-role="backButton"]').hide();
				
			if ($('[data-role="nextButton"]').is(':hidden'))
				$('[data-role="nextButton"]').show();
		});
	};
	
	// Parser XML and create the cards
	// z-index to create diferents layers on the div content
	var parseXML = function()
	{
		$.get(filenamePath, function(xml){
			$(xml).find('card').each(function(index) {
				var z_index = 9999 - index;
				var card = '<section id="card_'+index+'" style="z-index:'+z_index+'"data-role="card"><h1>'+$(this).attr("title")+'</h1>';
	
				$(this).find('p').each(function(){
					card += '<p>'+$(this).text()+'</p>';
				})
				
				card += '</section>';
				
				$('[data-role="content"]').append(card);
				current = index;
            });
			$('[data-role="card"]').first().addClass("selected");
			return lastCard = current;
		});
	};
	
	// Init class
	var start = function()
	{
		parseXML();
		nextCard();
		backCard();
		resize();
	};
	
	return {
		'ignition': start
	}

})();

$(function(){ /* DOM ready */
	Cards.ignition();
});
