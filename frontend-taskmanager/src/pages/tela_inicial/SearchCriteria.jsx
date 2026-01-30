import React from 'react';
import { Dropdown } from 'react-bootstrap';

const SearchCriteriaDropdown = ({ selectedCriteria, onCriteriaChange }) => {
  const searchCriteria = [
    { label: 'Todos', value: 'all' },
    { label: 'Nome da Tarefa', value: 'title' },
    { label: 'Descrição', value: 'description' },
    { label: 'Categoria', value: 'category' },
    { label: 'Status', value: 'status' }
  ];

  const getCurrentLabel = () => {
    const criteria = searchCriteria.find(c => c.value === selectedCriteria);
    return criteria ? criteria.label : 'Todos';
  };

  return (
    <Dropdown>
      <Dropdown.Toggle 
        variant="outline-secondary" 
        id="search-criteria-dropdown"
        className="ml-0 pl-0"
      >
        {getCurrentLabel()}
      </Dropdown.Toggle>

      <Dropdown.Menu>
        {searchCriteria.map((criteria) => (
          <Dropdown.Item 
            key={criteria.value} 
            onClick={() => onCriteriaChange(criteria.value)}
            active={selectedCriteria === criteria.value}
          >
            {criteria.label}
          </Dropdown.Item>
        ))}
      </Dropdown.Menu>
    </Dropdown>
  );
};

export default SearchCriteriaDropdown;