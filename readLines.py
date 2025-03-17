import os

def count_lines_of_code(file_path):
    """Conta il numero di righe non vuote in un file .java"""
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            lines = file.readlines()
            return len([line for line in lines if line.strip()])  # Conta solo le righe non vuote
    except Exception as e:
        print(f"Errore durante la lettura del file {file_path}: {e}")
        return 0

def count_lines_in_directory(directory):
    """Conta le linee di codice per ogni file .java in una directory e le sue sottodirectory"""
    total_lines = 0
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                lines = count_lines_of_code(file_path)
                if lines > 0:
                    print(f"{file_path}: {lines} linee di codice")
                    total_lines += lines
    return total_lines

def main():
    # Chiedi all'utente di inserire il percorso della directory
    directory = input("Inserisci il percorso della directory di partenza: ")

    if os.path.isdir(directory):
        total_lines = count_lines_in_directory(directory)
        print(f"\nTotale linee di codice in tutti i file .java: {total_lines}")
    else:
        print("Il percorso fornito non è una directory valida!")

if __name__ == "__main__":
    main()
