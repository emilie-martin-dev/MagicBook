package magic_book.core.requirement;

import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.RequirementJson;
import magic_book.core.game.BookState;
import magic_book.core.parser.Descriptible;

public abstract class AbstractRequirement implements Descriptible, JsonExportable<RequirementJson>{

	public abstract boolean isSatisfied(BookState state);
	
}
