package il.haifa.ac.dh.tikkounsofrim.model;

public class ManuscriptPlace {
	public ManuscriptID manuscriptId;
	public int page;
	public int line;

	public ManuscriptPlace(String manuscriptId, int page, int line) {
		this.manuscriptId = new ManuscriptID(manuscriptId);
		this.page = page;
		this.line = line;
	}

	public ManuscriptPlace getNext(int totalLines, int totalPages) {
		int currentLineNumber = this.line;
		int currentPage = this.page;
		currentLineNumber++;
		if (currentLineNumber <= totalLines) {
			// More lines to go in this page
			return new ManuscriptPlace(manuscriptId.getName(), currentPage, currentLineNumber);
		} else {
			// no more lines in page - go to the first line in the next page
			currentLineNumber = 1;
			currentPage++;
			if (currentPage >= totalPages) {
				// page numbers start with 1, not 0
				currentPage = (currentPage + 1) - totalPages;
			}
			return new ManuscriptPlace(manuscriptId.getName(), currentPage, currentLineNumber);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + line;
		result = prime * result + ((manuscriptId == null) ? 0 : manuscriptId.getName().hashCode());
		result = prime * result + page;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManuscriptPlace other = (ManuscriptPlace) obj;
		if (line != other.line)
			return false;
		if (manuscriptId == null) {
			if (other.manuscriptId != null)
				return false;
		} else if (!manuscriptId.getName().equals(other.manuscriptId.getName()))
			return false;
		if (page != other.page)
			return false;
		return true;
	}
}