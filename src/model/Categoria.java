package model;

public enum Categoria {
	
	
	JOGOS_TABULEIRO("Jogos de Tabuleiro"), JOGOS_PEDAGOGICOS("Jogos Pedagógicos"),
	BLOCOS_MONTAR("Blocos de montar"), BONECOS("Bonecos"), AR_LIVRE("Ar livre"), QUEBRA_CABECAS("Quebra-cabeças"),
	CARTAS("Cartas");
	

	private String descricao;

	Categoria(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return descricao; // isso aparece no ComboBox
	}
}
